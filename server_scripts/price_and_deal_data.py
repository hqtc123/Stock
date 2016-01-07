__author__ = 'Qing'
import urllib.request
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from datetime import date
import uuid
import sys

sys.path.append("/home/hqdo/Stock")
from model import *
import db_config

engine = create_engine(db_config.MYSQL_URI, pool_recycle=3600)
DBSession = sessionmaker(bind=engine)
session = DBSession()

SHANGHAI = {"market_type": 1, "page_count": 20}
SHENZHEN = {"market_type": 2, "page_count": 30}

PAGE_SIZE = 50

market = [SHENZHEN, SHANGHAI]

price_list = []
deal_info_list = []
base_url = "http://hq.sinajs.cn/list="
index_code_arr = ["sh000001", "sz399001", "sz399006"]
duplicate = True

for code in index_code_arr:
    url = base_url + code
    change_url = base_url + "s_" + code
    data_arr = urllib.request.urlopen(url).read().decode("gbk").split("=")[1].split(",")
    price_arr = urllib.request.urlopen(change_url).read().decode("gbk").split("=")[1].split(",")

    price = PriceEntity(
        id=str(uuid.uuid4()),
        code=code,
        now=data_arr[3],
        diff_percentage=price_arr[3],
        last_finish=data_arr[2],
        start=data_arr[1],
        highest=data_arr[4],
        lowest=data_arr[5],
        date=date.today()
    )
    price_list.append(price)

    deal_info = DealInfoEntity(
        id=str(uuid.uuid4()),
        code=code,
        num=data_arr[8],
        money=data_arr[9],
        ratio=1,
        date=date.today()
    )
    deal_info_list.append(deal_info)
    # todo exit when get same data with yesterday
    last_price = session.query(PriceEntity).filter_by(code=code).order_by('date desc').first()
    if last_price is None:
        duplicate = False
    else:
        if round(last_price.now) != round(float(price.now)):
            duplicate = False

if duplicate:
    print("duplicate")
    exit()
else:
    # get price and deal data from hexun.com
    for market in market:
        for page in range(1, market["page_count"] + 1):
            url = 'http://quote.tool.hexun.com/hqzx/quote.aspx?market=' + str(
                market["market_type"]) + '&sorttype=0&updown=down&page=' + str(page) + '&count=' + str(PAGE_SIZE)
            f = urllib.request.urlopen(url)
            content = f.read()
            if content.strip():
                data = content.decode("gbk")
                stock_list = data[12:].split(']')
                length = len(stock_list)

                for i in range(0, length - 2):
                    stock_str = stock_list[i].split('[')[1]
                    stock_item_list = stock_str.split(',')
                    price_entity = PriceEntity(
                        id=str(uuid.uuid4()),
                        code=stock_item_list[0][1:-1],
                        now=stock_item_list[2],
                        diff_percentage=stock_item_list[3],
                        last_finish=stock_item_list[4],
                        start=stock_item_list[5],
                        highest=stock_item_list[6],
                        lowest=stock_item_list[7],
                        date=date.today()
                    )
                    session.query(CompanyEntity).filter(CompanyEntity.code == price_entity.code).update(
                        {CompanyEntity.last_price_id: price_entity.id}
                    )
                    price_list.append(price_entity)
                    deal_info_entity = DealInfoEntity(
                        id=str(uuid.uuid4()),
                        code=stock_item_list[0][1:-1],
                        num=stock_item_list[8],
                        money=stock_item_list[9],
                        ratio=stock_item_list[10],
                        date=date.today()
                    )
                    deal_info_list.append(deal_info_entity)

    session.add_all(price_list)
    session.add_all(deal_info_list)
    session.commit()
session.close()
