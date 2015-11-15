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
    now = FloatField()
    diff_percentage = FloatField()
    last_finish = FloatField()
    start = FloatField()
    highest = FloatField()
    lowest = FloatField()
    date = DateField()


class DealInfo(BaseModel):
    class Meta:
        db_table = "deal_info"

    id = CharField(primary_key=True)
    code = CharField()
    num = FloatField()
    money = FloatField()
    ratio = FloatField()
    date = DateField()
