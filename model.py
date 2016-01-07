__author__ = 'Qing'
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, String, Float, Integer, Date

Base = declarative_base()


class CompanyEntity(Base):
    __tablename__ = 'company'

    id = Column(String, primary_key=True)
    market_type = Column(Integer)
    name = Column(String)
    code = Column(String)
    last_price_id = Column(String)


class PriceEntity(Base):
    __tablename__ = "price"
    id = Column(String(50), primary_key=True)
    code = Column(String)
    now = Column(Float)
    diff_percentage = Column(Float)
    last_finish = Column(Float)
    start = Column(Float)
    highest = Column(Float)
    lowest = Column(Float)
    date = Column(Date)


class DealInfoEntity(Base):
    __tablename__ = "deal_info"

    id = Column(String, primary_key=True)
    code = Column(String)
    num = Column(Float)
    money = Column(Float)
    ratio = Column(Float)
    date = Column(Date)
