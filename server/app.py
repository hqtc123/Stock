__author__ = 'Qing'
from flask import Flask, url_for
from model import Company
import flask

app = Flask(__name__)


@app.route("/")
def index():
    return "Hello world"


@app.route("/all")
def url_for_static():
    companies_dict = Company.select().dicts()
    data_list = []
    for item in companies_dict:
        data_list.append(item)
    rs_dict = {
        "code": 0,
        "data": data_list
    }
    return flask.jsonify(rs_dict)


if __name__ == "__main__":
    app.run(debug=True)
