import requests
from datetime import datetime
from kafka_producer import send_to_kafka2
import time

CLIENT_ID = "19zwPizN26iliYHy73Jf"
CLIENT_SECRET = "aH1kfQkVIF"

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
        products_info = {
                    'name': item.get("title"),
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


################################## 전체 키워드 API 호출 #####################################
def naver_products_all():
    digital_list = ["TV", "냉장고", "세탁기", "청소기", "노트북", "데스크탑", "키보드", "마우스", "모니터"]
    furniture_list = ["침대", "쇼파", "책상", "옷장"]
    all_list = digital_list + furniture_list

    url = "https://openapi.naver.com/v1/search/shop.json"
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,  # 여기에 클라이언트 ID를 입력하세요
        "X-Naver-Client-Secret": CLIENT_SECRET  # 여기에 클라이언트 시크릿을 입력하세요
    }


    for query in all_list: 
        params = {
            "query": query,
            "display": 100
        }

         # 상위 카테고리 설정
        major_category = ""
        if query in digital_list:
            major_category = "디지털가전"
        elif query in furniture_list:
            major_category = "가구"

        # Product Id 설정
        id = 0
        if major_category == "디지털가전":
            id = 1
        elif major_category == "가구":
            id = 2
        elif major_category == "생활용품":
            id = 3
        else:
            id = 4
        
        response = requests.get(url, headers=headers, params=params)
        current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

        for item in response.json().get("items"):

            # Price history
            price_entry = {
                "timestamp": current_time,
                "price": item.get("lprice")
            }

            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": item.get("title"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            # Price 메시지
            price_message = {
                "id": f"price_{id}",
                "routing": f"product_{id}",
                "index_name": major_category,
                "price_history": [price_entry]
            }
            # print(products_info)

            send_to_kafka2(product_message, price_message, major_category)

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

            # Price history
            price_entry = {
                "timestamp": current_time,
                "price": item.get("lprice")
            }

            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": item.get("title"),
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            # Price 메시지
            price_message = {
                "id": f"price_{uuid}",
                "routing": f"product_{uuid}",
                "index_name": major_category,
                "price_history": [price_entry],
            }
            # print(item.get("title"))
            send_to_kafka2(product_message, price_message, topic)
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

            # Price history
            price_entry = {
                "timestamp": current_time,
                "price": item.get("lprice")
            }

            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": item.get("title"),
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            # Price 메시지
            price_message = {
                "id": f"price_{uuid}",
                "routing": f"product_{uuid}",
                "index_name": major_category,
                "price_history": [price_entry]
            }
            # print(products_info)

            send_to_kafka2(product_message, price_message, topic)
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

            # Price history
            price_entry = {
                "timestamp": current_time,
                "price": item.get("lprice")
            }

            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": item.get("title"),
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            # Price 메시지
            price_message = {
                "id": f"price_{uuid}",
                "routing": f"product_{uuid}",
                "index_name": major_category,
                "price_history": [price_entry]
            }
            # print(products_info)

            send_to_kafka2(product_message, price_message, topic)
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

            # Price history
            price_entry = {
                "timestamp": current_time,
                "price": item.get("lprice")
            }

            # Product 메시지
            product_message = {
                "id": f"product_{uuid}",
                "major_category": major_category,
                "minor_category": query,
                "product_name": item.get("title"),
                "price": item.get("lprice"),
                "index_name": major_category,
                "product_link": item.get("link"),
                "product_image": item.get("image"),
                'provider' : item.get("mallName"),
                "last_update" : current_time
            }

            # Price 메시지
            price_message = {
                "id": f"price_{uuid}",
                "routing": f"product_{uuid}",
                "index_name": major_category,
                "price_history": [price_entry]
            }
            # print(products_info)

            send_to_kafka2(product_message, price_message, topic)
            cnt += 1
        # if response.status_code == 200:
        #     return response.json()
        # else:
        #     return {"error": "Request to Naver API failed"}
