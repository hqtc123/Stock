__author__ = 'Qing'
import urllib
import urllib.request
from company import insert_companies

SHANGHAI = 1
SHENZHEN = 2

for market_type in (SHANGHAI, SHENZHEN):
    url = 'http://quote.tool.hexun.com/hqzx/quote.aspx?market=' + str(
        market_type) + '&sorttype=0&updown=down&page=1&count=5'
    f = urllib.request.urlopen(url)
    data = f.read().decode(f.headers.get_content_charset())
    stock_list = data[12:].split(']')
    length = len(stock_list)

    company_dict_list = []
    for i in range(0, length - 2):
        stock_str = stock_list[i].split('[')[1]
        stock_item_list = stock_str.split(',')
        company_dict_list.append({"market_type": market_type, "code": stock_item_list[0], "name": stock_item_list[1]})

insert_companies(company_dict_list)
