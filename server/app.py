__author__ = 'Qing'
import flask
from flask import Flask
from flask.ext.cors import CORS
from model import Company

app = Flask(__name__)
CORS(app, supports_credentials=True)


@app.route("/companies", methods=["POST", "GET"])
def companies():
    # companies_dict = Company.select().paginate(1, 10).dicts()
    # data_list = []
    # for item in companies_dict:
    #     data_list.append(item)
    rs_dict = {
        "code": 0,
        "data": ""
    }
    return flask.jsonify(rs_dict)


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
