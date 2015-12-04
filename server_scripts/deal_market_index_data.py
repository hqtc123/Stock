__author__ = 'Qing'
import urllib
import urllib.request
from datetime import date
import uuid
import sys

sys.path.append("..")
from model import Price
from model import DealInfo

# 获取三大指数的数据

index_price_list = []
index_deal_info_list = []
base_url = "http://hq.sinajs.cn/list="
index_code_arr = ["sh000001", "sz399001", "sz399006"]

for code in index_code_arr:
    url = base_url + code
    change_url = base_url + "s_" + code
    data_arr = urllib.request.urlopen(url).read().decode("gbk").split("=")[1].split(",")
    price_arr = urllib.request.urlopen(change_url).read().decode("gbk").split("=")[1].split(",")
    print(data_arr)
    print(price_arr)
    price_dict = {
        "id": uuid.uuid4(),
        "code": code,
        "now": data_arr[3],
        "diff_percentage": price_arr[3],
        "last_finish": data_arr[2],
        "start": data_arr[1],
        "highest": data_arr[4],
        "lowest": data_arr[5],
        "date": date.today()
    }
    index_price_list.append(price_dict)

    deal_info_dict = {
        "id": uuid.uuid4(),
        "code": code,
        "num": data_arr[8],
        "money": data_arr[9],
        "ratio": 1,
        "date": date.today()
    }
    index_deal_info_list.append(deal_info_dict)
    # todo exit when get same data with yesterday
exit()
