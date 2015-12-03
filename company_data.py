__author__ = 'Qing'
import urllib
import urllib.request

from server.model import Company

SHANGHAI = {"market_type": 1, "page_count": 20}
SHENZHEN = {"market_type": 2, "page_count": 30}

PAGE_SIZE = 50

company_list = []
market = [SHENZHEN, SHANGHAI]

for market in market:
    for page in range(1, market["page_count"] + 1):
        url = 'http://quote.tool.hexun.com/hqzx/quote.aspx?market=' + str(
            market["market_type"]) + '&sorttype=0&updown=down&page=' + str(page) + '&count=' + str(PAGE_SIZE)
        print(url)
        f = urllib.request.urlopen(url)
        content = f.read()
        if content.strip():
            data = content.decode("gbk")
            stock_list = data[12:].split(']')
            length = len(stock_list)

            for i in range(0, length - 2):
                stock_str = stock_list[i].split('[')[1]
                stock_item_list = stock_str.split(',')
                company_list.append({"market_type": market["market_type"], "code": stock_item_list[0][1:-1],
                                     "name": stock_item_list[1][1:-1]})

Company.insert_many(company_list).execute()
