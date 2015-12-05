__author__ = 'Qing'
from peewee import *
from db import Db

db_instance = Db()
database = db_instance.get_database()


class BaseModel(Model):
    class Meta:
        database = database


class Company(BaseModel):
    id = IntegerField(primary_key=True)
    market_type = IntegerField()
    code = CharField()
    name = CharField()


class Price(BaseModel):
    id = CharField(primary_key=True)
    code = CharField()
    now = DoubleField()
    diff_percentage = DoubleField()
    last_finish = DoubleField()
    start = DoubleField()
    highest = DoubleField()
    lowest = DoubleField()
    date = DateField()


class DealInfo(BaseModel):
    class Meta:
        db_table = "deal_info"

    id = CharField(primary_key=True)
    code = CharField()
    num = DoubleField()
    money = DoubleField()
    ratio = DoubleField()
    date = DateField()
