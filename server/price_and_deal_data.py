__author__ = 'Qing'
import urllib
import urllib.request
from datetime import date
import uuid

from server.model import Price
from server.model import DealInfo

SHANGHAI = {"market_type": 1, "page_count": 20}
SHENZHEN = {"market_type": 2, "page_count": 30}

PAGE_SIZE = 50

price_list = []
deal_info_list = []
market = [SHENZHEN, SHANGHAI]

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
                price_dict = {
                    "id": uuid.uuid4(),
                    "code": stock_item_list[0][1:-1],
                    "now": stock_item_list[2],
                    "diff_percentage": stock_item_list[3],
                    "last_finish": stock_item_list[4],
                    "start": stock_item_list[5],
                    "highest": stock_item_list[6],
                    "lowest": stock_item_list[7],
                    "date": date.today()
                }
                price_list.append(price_dict)
                deal_info_dict = {
                    "id": uuid.uuid4(),
                    "code": stock_item_list[0][1:-1],
                    "num": stock_item_list[8],
                    "money": stock_item_list[9],
                    "ratio": stock_item_list[10],
                    "date": date.today()
                }
                deal_info_list.append(deal_info_dict)

Price.insert_many(price_list).execute()
DealInfo.insert_many(deal_info_list).execute()
