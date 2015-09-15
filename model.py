__author__ = 'Qing'
from peewee import *

database = MySQLDatabase("stock", host="203.195.140.52", port=3306, user="root", passwd="qwerty")


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
