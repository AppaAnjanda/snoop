import requests
from datetime import datetime
from kafka_producer import send_to_kafka
from pydantic import BaseModel
import time
import re

CLIENT_ID = "19zwPizN26iliYHy73Jf"
CLIENT_SECRET = "aH1kfQkVIF"

pattern = re.compile(r'<.*?>')

class KeywordDto(BaseModel):
    keyword : str

################################## 단일 키워드 API 호출 #####################################
def naver_product(query):
    digital_list = {"TV" : "1", "냉장고" : "2", "세탁기": "3", "청소기": "4", "노트북": "5", "데스크탑": "6", "키보드": "7", "마우스": "8", "모니터": "9"}
    furniture_list = {"침대" : "1", "쇼파" : "2", "책상" : "3", "옷장" : "4"}
    necessaries_list = {"주방" : "1", "욕실" : "2", "청소" : "3", "수납": "4"}
    food_list = {"음료" : "1", "과일" : "2", "채소": "3", "과자": "4", "축산": "5", "가공식품": "6"}

    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }

    # 상위 카테고리 설정
    major_category = ""
    # 하위 카테고리 설정
    minor_category = ""
    # Product Id 설정
    id = ""
    # 토픽 설정
    topic = ""
    product_list = []

    if query[0] == '1':
        major_category = "디지털가전"
        id = "1"
        topic = "digital"
        product_list = digital_list
    
        if query[1] == '1': minor_category = "TV"
        elif query[1] == '2': minor_category = "냉장고"
        elif query[1] == '3': minor_category = "세탁기"
        elif query[1] == '4': minor_category = "청소기"
        elif query[1] == '5': minor_category = "노트북"
        elif query[1] == '6': minor_category = "데스크탑"
        elif query[1] == '7': minor_category = "키보드"
        elif query[1] == '8': minor_category = "마우스"
        elif query[1] == '9': minor_category = "모니터"
            
    elif query[0] == '2':
        major_category = "가구"
        id = "2"
        topic = "furniture"
        product_list = furniture_list

        if query[1] == '1': minor_category = "침대"
        elif query[1] == '2': minor_category = "쇼파"
        elif query[1] == '3': minor_category = "책상"
        elif query[1] == '4': minor_category = "옷장"

    elif query[0] == '3':
        major_category = "생활용품"
        id = "3"
        topic = "necessaries"
        product_list = necessaries_list

        if query[1] == '1': minor_category = "주방"
        elif query[1] == '2': minor_category = "욕실"
        elif query[1] == '3': minor_category = "청소"
        elif query[1] == '4': minor_category = "수납"

    elif query[0] == '4':
        major_category = "식품"
        id = "4"
        topic = "food"
        product_list = food_list

        if query[1] == '1': minor_category = "음료"
        elif query[1] == '2': minor_category = "과일"
        elif query[1] == '3': minor_category = "채소"
        elif query[1] == '4': minor_category = "과자"
        elif query[1] == '5': minor_category = "축산"
        elif query[1] == '6': minor_category = "가공식품"
    
    params = {
        "query": query[2:],
        "display": 1
    }

    response = requests.get(url, headers=headers, params=params)
    current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')
    for item in response.json().get("items"):
        uuid = id + product_list.get(minor_category)
        name = item.get("title")
        re_name = re.sub(pattern, '', name)
        # Product 메시지
        product_message = {
            "code": f"{uuid}{re_name}",
            "majorCategory": major_category,
            "minorCategory": minor_category,
            "productName": re_name,
            "price": item.get("lprice"),
            "productLink": item.get("link"),
            "productImage": item.get("image"),
            'provider' : item.get("mallName")
        }

        send_to_kafka(product_message, topic)
        # print(re_name, major_category, minor_category)

    # if response.status_code == 200:
    #     return response.json()
    # else:
    #     return {"error": "Request to Naver API failed"}

################################## 디지털가전 키워드 API 호출 #####################################
def naver_products_digital():
    digital_list = {"TV" : "1", "냉장고" : "2", "세탁기": "3", "청소기": "4", "노트북": "5", "데스크탑": "6", "키보드": "7", "마우스": "8", "모니터": "9"}

    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }

    # 상위 카테고리 설정
    major_category = "디지털가전"
    # Product Id 설정
    id = "1"
    # 토픽 설정
    topic = "digital"
    for query in digital_list: 
        cnt = 1
        print(query)
        params = {
            "query": query,
            "display": 100
        }
        response = requests.get(url, headers=headers, params=params)
        current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

        for item in response.json().get("items"):
            uuid = id + digital_list.get(query)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "code": f"{uuid}{re_name}",
                "majorCategory": major_category,
                "minorCategory": query,
                "productName": re_name,
                "price": item.get("lprice"),
                "productLink": item.get("link"),
                "productImage": item.get("image"),
                'provider' : item.get("mallName")
            }

            send_to_kafka(product_message, topic)
        # if response.status_code == 200:
        #     return response.json()
        # else:
        #     return {"error": "Request to Naver API failed"}

################################## 가구 키워드 API 호출 #####################################
def naver_products_furniture():
    furniture_list = {"침대" : "1", "쇼파" : "2", "책상" : "3", "옷장" : "4"}

    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }

    # 상위 카테고리 설정
    major_category = "가구"

    # Product Id 설정
    id = "2"
    # 토픽 설정
    topic = "furniture"
    for query in furniture_list: 
        cnt = 1
        params = {
            "query": query,
            "display": 100
        }
        response = requests.get(url, headers=headers, params=params)
        current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

        for item in response.json().get("items"):
            uuid = id + furniture_list.get(query)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "code": f"{uuid}{re_name}",
                "majorCategory": major_category,
                "minorCategory": query,
                "productName": re_name,
                "price": item.get("lprice"),
                "productLink": item.get("link"),
                "productImage": item.get("image"),
                'provider' : item.get("mallName")
            }

            send_to_kafka(product_message, topic)
        # if response.status_code == 200:
        #     return response.json()
        # else:
        #     return {"error": "Request to Naver API failed"}

################################## 생활용품 키워드 API 호출 #####################################
def naver_products_necessaries():
    necessaries_list = {"주방" : "1", "욕실" : "2", "청소" : "3", "수납": "4"}

    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }

    # 상위 카테고리 설정
    major_category = "생활용품"
    # Product Id 설정
    id = "3"
    # 토픽 설정
    topic = "necessaries"
    for query in necessaries_list: 
        cnt = 1
        params = {
            "query": query,
            "display": 100
        }
        response = requests.get(url, headers=headers, params=params)
        current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

        for item in response.json().get("items"):
            uuid = id + necessaries_list.get(query)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "code": f"{uuid}{re_name}",
                "majorCategory": major_category,
                "minorCategory": query,
                "productName": re_name,
                "price": item.get("lprice"),
                "productLink": item.get("link"),
                "productImage": item.get("image"),
                'provider' : item.get("mallName")
            }

            send_to_kafka(product_message, topic)
        # if response.status_code == 200:
        #     return response.json()
        # else:
        #     return {"error": "Request to Naver API failed"}
################################## 식품 키워드 API 호출 #####################################
def naver_products_food():
    food_list = {"음료" : "1", "과일" : "2", "채소": "3", "과자": "4", "축산": "5", "가공식품": "6"}

    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }

    # 상위 카테고리 설정
    major_category = "식품"
    # Product Id 설정
    id = "4"
    # 토픽 설정
    topic = "food"
    for query in food_list: 
        cnt = 1
        params = {
            "query": query,
            "display": 100
        }
        response = requests.get(url, headers=headers, params=params)
        current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

        for item in response.json().get("items"):
            uuid = id + food_list.get(query)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "code": f"{uuid}{re_name}",
                "majorCategory": major_category,
                "minorCategory": query,
                "productName": re_name,
                "price": item.get("lprice"),
                "productLink": item.get("link"),
                "productImage": item.get("image"),
                'provider' : item.get("mallName")
            }

            send_to_kafka(product_message, topic)
        # if response.status_code == 200:
        #     return response.json()
        # else:
        #     return {"error": "Request to Naver API failed"}
