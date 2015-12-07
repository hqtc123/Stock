__author__ = 'Qing'
import flask
from flask import Flask
from flask import make_response, abort
from model import Company
from model import Price
from db import Db
from playhouse.shortcuts import model_to_dict

app = Flask(__name__)
db_instance = Db()


@app.route("/")
@app.route("/index")
@app.route("/about")
def basic_pages(**kwargs):
    return make_response(open("index.html", 'rb').read())


@app.route("/api/companies/<page>", methods=["POST"])
def companies(page):
    companies_dicts = Company.select().order_by(Company.code).paginate(int(page), 20).dicts()
    data_list = []
    for item in companies_dicts:
        price = Price.select().where(Price.code == item["code"]).order_by(Price.date.desc()).limit(1).get()
        price.date = str(price.date)
        item["price"] = model_to_dict(price)
        data_list.append(item)
    rs_dict = {
        "code": 0,
        "data": data_list
    }
    return flask.jsonify(rs_dict)


@app.route("/api/company_price/<code>", methods=["POST"])
def company_price(code):
    price_dicts = Price.select().where(Price.code == code).order_by(Price.date.desc()).paginate(1, 20).dicts()
    data_list = []
    for item in price_dicts:
        item["date"] = str(item["date"])
        data_list.append(item)
    rs_dict = {
        "code": 0,
        "data": data_list
    }
    return flask.jsonify(rs_dict)


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
