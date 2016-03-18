from pymongo import MongoClient

if __name__ == "__main__":
    client = MongoClient("localhost", 27017)
    db = client.ts_db
    rs_arr = []
    for doc in db.score5.find().sort([("value", -1)]).limit(10):
        print(doc)
