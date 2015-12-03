__author__ = 'Qing'
import flask
from flask import Flask
from flask import make_response, abort
from model import Company
from db import Db

app = Flask(__name__)
db_instance = Db()


@app.route("/")
@app.route("/index")
@app.route("/about")
def basic_pages(**kwargs):
    return make_response(open("index.html", 'rb').read())


@app.route("/companies", methods=["POST", "GET"])
def companies():
    companies_dict = Company.select().paginate(1, 10).dicts()
    data_list = []
    for item in companies_dict:
        data_list.append(item)
    rs_dict = {
        "code": 0,
        "data": data_list
    }
    return flask.jsonify(rs_dict)


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
