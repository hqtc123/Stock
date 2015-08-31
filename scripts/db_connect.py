__author__ = 'Qing'
import mysql.connector
from mysql.connector import Error,MySQLConnection
from db_config import read_db_config


def connect():
    db_config = read_db_config()

    try:
        print("Connecting");
        conn = MySQLConnection(**db_config)

        if conn.is_connected():
            print("connect success")
        else:
            print("connect failed")

    except Error as error:
        print(error)
    finally:
        conn.close()
        print("connect closed.")


connect()

