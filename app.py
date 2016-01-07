__author__ = 'Qing'
import flask
from flask import Flask
from flask import make_response
import re
from model import *
import db_config
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm.exc import NoResultFound

app = Flask(__name__)

engine = create_engine(db_config.MYSQL_URI, pool_recycle=3600)
DBSession = sessionmaker(bind=engine)


@app.route("/")
@app.route("/index")
@app.route("/about")
def basic_pages(**kwargs):
    return make_response(open("index.html", 'rb').read())


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


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
