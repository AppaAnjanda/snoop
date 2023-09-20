import requests
from datetime import datetime
from kafka_producer import send_to_kafka
import time
import re

CLIENT_ID = "19zwPizN26iliYHy73Jf"
CLIENT_SECRET = "aH1kfQkVIF"

pattern = re.compile(r'<.*?>')

################################## 단일 키워드 API 호출 #####################################
def naver_products(query):
    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }

    params = {
        "query": query,
        "display": 100
        }

    response = requests.get(url, headers=headers, params=params)
    for item in response.json().get("items"):
        name = item.get("title")
        re_name = re.sub(pattern, '', name)
        products_info = {
                    'name': re_name,
                    'base_price': 0,
                    'price': item.get("lprice"),
                    'product_url': item.get("link"),
                    'image': item.get("image"),
                    'major_category' : item.get("category1"),
                    'minor_category' : query,
                    'provider': item.get("mallName"),
                }
        # print(products_info)

    if response.status_code == 200:
        return response.json()
    else:
        return {"error": "Request to Naver API failed"}

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
            uuid = id + digital_list.get(query) + str(cnt)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": re_name,
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            send_to_kafka(product_message, topic)
            cnt += 1
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
            uuid = id + furniture_list.get(query) + str(cnt)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": re_name,
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            send_to_kafka(product_message, topic)
            cnt += 1
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
            uuid = id + necessaries_list.get(query) + str(cnt)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": re_name,
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            send_to_kafka(product_message, topic)
            cnt += 1
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
            uuid = id + food_list.get(query) + str(cnt)
            name = item.get("title")
            re_name = re.sub(pattern, '', name)
            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": re_name,
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            send_to_kafka(product_message, topic)
            cnt += 1
        # if response.status_code == 200:
        #     return response.json()
        # else:
        #     return {"error": "Request to Naver API failed"}
