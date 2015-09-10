__author__ = 'Qing'
from mysql.connector import MySQLConnection, Error

from scripts.db_config import read_db_config


def insert_company(market_type, code, name):
    query_sql = "insert into company(market_type, code, name) VALUES (d%,s%,s%)"
    args = (market_type, code, name)

    try:
        db_config = read_db_config()
        conn = MySQLConnection(**db_config)

        cursor = conn.cursor()
        cursor.execute(query_sql, args)

        conn.commit()
    except Error as error:
        print(error)

    finally:
        cursor.close()
        conn.close()


def insert_companies(company_dict_list):
    try:
        db_config = read_db_config()
        conn = MySQLConnection(**db_config)
        cursor = conn.cursor()

        for company_dict in company_dict_list:
            query_sql = "insert into company(market_type, code, name) VALUES (%s,%s,%s)"
            args = (company_dict["market_type"], company_dict["code"], company_dict["name"])
            cursor.execute(query_sql, args)
            conn.commit()

    except Error as e:
        print(e)
