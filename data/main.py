from fastapi import FastAPI
from apscheduler.schedulers.background import BackgroundScheduler
from coupang import coupang_products, coupang_products_digital, coupang_products_furniture, coupang_products_necessaries, coupang_products_food
from naver import naver_products, naver_products_digital, naver_products_furniture, naver_products_necessaries, naver_products_food
import pandas as pd
import requests

digital_list = ["TV", "냉장고", "세탁기", "청소기", "노트북", "데스크탑", "키보드", "마우스", "모니터"]
furniture_list = ["침대", "쇼파", "책상", "옷장"]
necessaries_list = ["주방","욕실","청소","수납"]
food_list = ["생수/음료","과일/채소","과자","축산/계란","가공식품"]

# fast api 서버
app = FastAPI()

########################################## 쿠팡 #####################################################

# 쿠팡 크롤링 함수
@app.get("/coupang")
def coupang(query:str):
    default_page = 27
    df = coupang_products(query, default_page)
    return {"message": "Crawling completed!"}

@app.get("/coupang/all")
def coupang_all():
    df = coupang_products_all()
    return {"message": "Crawling completed!"}

@app.get("/coupang/digital")
def coupang_digital():
    return coupang_products_digital()

@app.get("/coupang/furniture")
def coupang_furniture():
    return coupang_products_furniture()

@app.get("/coupang/necessaries")
def coupang_necessaries():
    return coupang_products_necessaries()

@app.get("/coupang/food")
def coupang_food():
    return coupang_products_food()
########################################## 네이버 #####################################################

# 네이버 쇼핑 API 함수
@app.get("/naver")
def naver(query:str):
    return naver_products(query)

# 네이버 쇼핑 API 전체 조회 함수
@app.get("/naver/all")
def naver_all():
    return naver_products_all()

# 네이버 쇼핑 디지털가전 API 전체 조회 함수
@app.get("/naver/digital")
def naver_digital():
    return naver_products_digital()

# 네이버 쇼핑 가구 API 전체 조회 함수
@app.get("/naver/furniture")
def naver_furniture():
    return naver_products_furniture()

# 네이버 쇼핑 생활용품 API 전체 조회 함수
@app.get("/naver/necessaries")
def naver_necessaries():
    return naver_products_necessaries()

# 네이버 쇼핑 식품 API 전체 조회 함수
@app.get("/naver/food")
def naver_food():
    return naver_products_food()


# 스케줄러 설정
# scheduler = BackgroundScheduler()
# scheduler.add_job(naver_digital, "interval", minutes=60)
# scheduler.add_job(naver_furniture, "interval", minutes=60)
# scheduler.add_job(naver_necessaries, "interval", minutes=60)
# scheduler.add_job(naver_food, "interval", minutes=60)
# scheduler.start()

# 메인함수에 설정해서 자동 실행되도록
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)