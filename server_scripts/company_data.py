__author__ = 'Qing'
import urllib.request
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm.exc import NoResultFound
import uuid
import sys

sys.path.append("/home/hqdo/Stock")
from model import CompanyEntity
import db_config

engine = create_engine(db_config.MYSQL_URI, pool_recycle=3600)
DBSession = sessionmaker(bind=engine)
session = DBSession()

SHANGHAI = {"market_type": 1, "page_count": 20}
SHENZHEN = {"market_type": 2, "page_count": 30}

PAGE_SIZE = 50

company_list = []
market = [SHENZHEN, SHANGHAI]

for market in market:
    for page in range(1, market["page_count"] + 1):
        url = 'http://quote.tool.hexun.com/hqzx/quote.aspx?market=' + str(
            market["market_type"]) + '&sorttype=0&updown=down&page=' + str(page) + '&count=' + str(PAGE_SIZE)
        # print(url)
        f = urllib.request.urlopen(url)
        content = f.read()
        if content.strip():
            data = content.decode("gbk")
            stock_list = data[12:].split(']')
            length = len(stock_list)

            for i in range(0, length - 2):
                stock_str = stock_list[i].split('[')[1]
                stock_item_list = stock_str.split(',')
                company = CompanyEntity(
                    id=str(uuid.uuid4()),
                    market_type=market["market_type"],
                    code=stock_item_list[0][1:-1],
                    name=stock_item_list[1][1:-1]
                )
                try:
                    query = session.query(CompanyEntity).filter_by(code=company.code)
                    old_company = query.first()
                    if company.name != old_company.name:
                        company.id = old_company.id
                        session.merge(company)
                except NoResultFound:
                    company_list.append(company)

if len(company_list) != 0:
    session.add_all(company_list)

session.commit()
session.close()
