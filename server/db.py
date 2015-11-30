__author__ = 'Qing'
from playhouse.pool import PooledMySQLDatabase


class Db:
    database = PooledMySQLDatabase("stock", host="127.0.0.1", port=3306, user="root", passwd="qwerty")

    def __new__(cls, *args, **kwargs):
        if "_instance" not in vars(cls):
            cls._instance = super(Db, cls).__new__(cls, *args, **kwargs)
            return cls._instance

    def get_database(self):
        return self.database

    def connect(self):
        self.database.connect()

    def close(self):
        if not self.database.is_closed():
            self.database.close()
