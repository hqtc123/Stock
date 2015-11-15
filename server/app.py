__author__ = 'Qing'
import flask
from flask import Flask
from flask.ext.cors import CORS
from model import Company
from db import Db

app = Flask(__name__)
CORS(app, supports_credentials=True)
db_instance = Db()


@app.route("/companies", methods=["POST", "GET"])
def companies():
    companies_dict = Company.select().paginate(1, 10).dicts()
    data_list = []
    for item in companies_dict:
        data_list.append(item)
    rs_dict = {
        "code": 0,
        "data": companies_dict
    }
    return flask.jsonify(rs_dict)


@app.before_request
def _db_connect():
    print(type(db_instance))
    db_instance.connect()


@app.teardown_request
def _db_close():
    db_instance.close()

if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
