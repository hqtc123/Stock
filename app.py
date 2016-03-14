__author__ = 'Qing'
# -*- coding: UTF-8 -*-
import flask
from flask import Flask, request
from flask import make_response
import re
import os
from model import *
import app_config
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm.exc import NoResultFound

from pymongo import MongoClient

app = Flask(__name__)

engine = create_engine(app_config.MYSQL_URI, pool_recycle=3600)
DBSession = sessionmaker(bind=engine)

ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'zip'])

SERVER_ERR_DICT = {"code": 1, "data": "server error"}
DEFAULT_RS_DICT = {"code": 0, "data": "success"}


@app.route("/")
@app.route("/index")
@app.route("/about")
def basic_pages(**kwargs):
    return make_response(open("index.html", 'rb').read())


@app.route("/jplag_result")
def jplag_result(**kwargs):
    return make_response(open("jplag_result/index.html", 'rb').read())


@app.route("/api/companies/<page>", methods=["POST"])
def list_companies(page=1):
    session = DBSession()
    page_size = 20
    companies_count = session.query(CompanyEntity).count()
    companies = session.query(CompanyEntity).order_by(CompanyEntity.code).limit(page_size).offset(
        page_size * (int(page) - 1)).all()
    company_list = []
    for company in companies:
        try:
            price = session.query(PriceEntity).filter_by(id=company.last_price_id).one()
        except NoResultFound:
            price = PriceEntity(
                code=company.code,
                now="",
                diff_percentage="",
                last_finish="",
                start="",
                highest="",
                lowest="",
                date=""
            )
        price.date = str(price.date)
        price.__dict__.pop("_sa_instance_state", None)
        company.price = price.__dict__
        company.__dict__.pop("_sa_instance_state", None)
        company_list.append(company.__dict__)
        rs_dict = {
            "code": 0,
            "data": {
                "totalCount": companies_count,
                "companies": company_list
            }
        }
    session.close()
    return flask.jsonify(rs_dict)


@app.route("/api/company_price/<code>", methods=["POST"])
def company_price(code):
    session = DBSession()
    prices = session.query(PriceEntity).filter_by(code=code).order_by(PriceEntity.date).all()
    price_list = []
    for price in prices:
        price.date = str(price.date)
        price.__dict__.pop("_sa_instance_state", None)
        price_list.append(price.__dict__)
    rs_dict = {
        "code": 0,
        "data": price_list
    }
    session.close()
    return flask.jsonify(rs_dict)


@app.route("/api/companies/search/<keyword>", methods=["POST"])
def companies_search(keyword):
    session = DBSession()
    rs_dict = {
        "code": 0,
        "data": []
    }
    if len(keyword) > 6 or len(keyword) < 2:
        return flask.jsonify(rs_dict)

    digit_match = re.match("[0-9]{1,6}", keyword)
    code_match = re.match("[0-9]{4,6}", keyword)
    if code_match is not None:
        companies = session.query(CompanyEntity).filter(CompanyEntity.code.contains(keyword)).limit(10).all()
        for company in companies:
            company.__dict__.pop("_sa_instance_state", None)
            rs_dict["data"].append(company.__dict__)

    else:
        if digit_match is not None:
            pass
        else:
            companies = session.query(CompanyEntity).filter(CompanyEntity.name.contains(keyword)).limit(10).all()
            for company in companies:
                company.__dict__.pop("_sa_instance_state", None)
                rs_dict["data"].append(company.__dict__)
    session.close()
    return flask.jsonify(rs_dict)


@app.route("/api/company/get/<code>", methods=["POST"])
def get_company(code):
    session = DBSession()
    try:
        company = session.query(CompanyEntity).filter_by(code=code).first()
        company.__dict__.pop("_sa_instance_state", None)
        rs_dict = {
            "code": "0",
            "data": company.__dict__
        }
    except NoResultFound:
        rs_dict = {
            "code": "1",
            "data": "company not found"
        }
    session.close()
    return flask.jsonify(rs_dict)


@app.errorhandler(500)
def internal_error(error):
    return "internal error " + str(error)


@app.route("/api/poem/search/<keyword>/<interval>", methods=["POST"])
def search_allusion(keyword, interval):
    client = MongoClient("localhost", 27017)
    db = client.ts_db
    index_collection = db["inverted_index"]
    poem_collection = db["ts_co2"]
    rs_dict = {
        "code": 0,
        "data": {"msg": "not found", "count": 0}
    }

    interval = -1 if len(interval) == 0 else int(interval)
    keyword = keyword.replace(" ", "+")
    # 搜索两个典面
    if "+" in keyword:
        key_list = keyword.split("+")
        if len(key_list) != 2:
            return flask.jsonify({"code": 1, "data": "输入格式不正确"})
        (key1, key2) = (key_list[0], key_list[1])
        # 查询两个典故的索引列表
        rs_doc1 = index_collection.find_one({"_id": key1})
        rs_doc2 = index_collection.find_one({"_id": key2})
        if rs_doc1 is None or rs_doc2 is None:
            return flask.jsonify(rs_dict)
        (poem_list1, poem_list2) = (rs_doc1["value"]["arr"], rs_doc2["value"]["arr"])
        rs_dict["data"] = {"count": 0, "poems": []}
        # 遍历查询的索引列表，求出给定条件的集合
        for poem1 in poem_list1:
            for poem2 in poem_list2:
                if poem1["poem_id"] == poem2["poem_id"]:
                    if interval > 0:
                        real_interval = poem1["position"] - poem2["position"] - len(key2)
                        if real_interval < 0:
                            real_interval = poem2["position"] - poem1["position"] - len(key1)
                        if real_interval > interval:
                            continue

                    poem_doc = poem_collection.find_one({"_id": poem1["poem_id"]})
                    rs_dict["data"]["poems"].append({
                        "title": poem_doc["title"],
                        "author": poem_doc["author"],
                        "sentence": poem_doc["content_arr"][int(poem1["sentence_index"])] + " & " +
                                    poem_doc["content_arr"][int(poem2["sentence_index"])],
                        "content": poem_doc["content"],
                        "category": poem_doc["category"]
                    })
        rs_dict["data"]["count"] = len(rs_dict["data"]["poems"])
        return flask.jsonify(rs_dict)

    # 搜索单个典面
    keyword1 = keyword[0:3] if len(keyword) > 3 else keyword
    rs_doc = index_collection.find_one({"_id": keyword1})
    if rs_doc is None:
        return flask.jsonify(rs_dict)
    poem_list = rs_doc["value"]["arr"]
    rs_dict["data"] = {"count": 0, "poems": []}

    for poem in poem_list:
        poem_doc = poem_collection.find_one({"_id": poem["poem_id"]})
        if poem_doc is None:
            rs_dict["data"]["poems"].append(str(poem["poem_id"]))
            continue
        if len(keyword) > 3:
            if keyword not in poem_doc["content_arr"][int(poem["sentence_index"])]:
                continue
        rs_dict["data"]["poems"].append({
            "title": poem_doc["title"],
            "author": poem_doc["author"],
            "sentence": poem_doc["content_arr"][int(poem["sentence_index"])],
            # "content": poem_doc["content"],
            "category": poem_doc["category"]
        })
    rs_dict["data"]["count"] = len(rs_dict["data"]["poems"])
    return flask.jsonify(rs_dict)


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS


@app.route("/api/upload", methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        file = request.files['file']
        if file and allowed_file(file.filename):
            filename = file.filename
            file.save(os.path.join(app_config.UPLOAD_FOLDER, filename))
            rs_dict = DEFAULT_RS_DICT
            rs_dict["data"] = {"fileName": file.filename}
            return flask.jsonify(rs_dict)
    return flask.jsonify(SERVER_ERR_DICT)


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
