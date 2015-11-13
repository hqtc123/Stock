__author__ = 'Qing'
import flask
from flask import Flask
from model import Company

app = Flask(__name__)


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


@app.after_request
def after_request(response):
    response.headers.add('Access-Control-Allow-Origin', 'http://localhost:63342')
    response.headers.add('Access-Control-Allow-Credentials', "true")
    response.headers.add('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE')
    return response


if __name__ == "__main__":
    app.run(debug=True)
