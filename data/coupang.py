import requests
from bs4 import BeautifulSoup
import pandas as pd
import time
import json
from kafka_producer import send_to_kafka
from datetime import datetime
import re

pattern = re.compile(r',')

################################## 단일 키워드 크롤링 #####################################
def coupang_product(query):
    digital_list = {"TV" : "1", "냉장고" : "2", "세탁기": "3", "청소기": "4", "노트북": "5", "데스크탑": "6", "키보드": "7", "마우스": "8", "모니터": "9"}
    furniture_list = {"침대" : "1", "쇼파" : "2", "책상" : "3", "옷장" : "4"}
    necessaries_list = {"주방" : "1", "욕실" : "2", "청소" : "3", "수납": "4"}
    food_list = {"음료" : "1", "과일" : "2", "채소": "3", "과자": "4", "축산": "5", "가공식품": "6"}

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
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

    url = f'https://www.coupang.com/np/search?q={query[2:]}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={1}&rocketAll=true'
    response = requests.get(url, headers=headers)
    time.sleep(0.3)

    soup = BeautifulSoup(response.content, 'html.parser')

    # if (soup.find('ul', id='productList')):
    products_list = soup.find('ul', id='productList').find_all('li', class_='search-product')  # Added class attribute

    print(query[2:], major_category, minor_category, topic)    

    li = products_list[0]
    uuid = id + product_list.get(minor_category)
    a_link = li.find('a', href=True)['href']
    prd_link = BASE_URL + a_link
    prd_name = li.find('div', class_='name').text.strip()

    image = li.find('img', class_='search-product-wrap-img').get('src')
    image_link = "https:"+image

    # base_price_element = li.find('del', class_='base-price')
    # base_price = base_price_element.text if base_price_element else ''

    price_element = li.find('strong', class_='price-value')
    price = price_element.text if price_element else ''

    current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')
    
    re_price = re.sub(pattern, '', price)

    # Product 메시지
    product_message = {
        "code": f"{uuid}{prd_name}",
        "majorCategory": major_category,
        "minorCategory": minor_category,
        "productName": prd_name,
        "price": int(re_price),
        "productLink": prd_link,
        "productImage": image_link,
        'provider' : '쿠팡'
    }

    # send_to_kafka(product_message, topic) # Kafka에 전송


################################## 디지털가전 키워드 크롤링 #####################################
def coupang_products_digital():
    digital_list = {"TV" : "1", "냉장고" : "2", "세탁기": "3", "청소기": "4", "노트북": "5", "데스크탑": "6", "키보드": "7", "마우스": "8", "모니터": "9"}

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
    }

    products_link = []
    print('Start Crawler')
    # 상위 카테고리 설정
    major_category = "디지털가전"
    # Product Id 설정
    id = "1"
    # 토픽 설정
    topic = "digital"
    for keyword in digital_list:
        cnt = 1        
        for page in range(1, 10):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=true'
            print('check url ' + url)

            response = requests.get(url, headers=headers)
            time.sleep(0.5)

            print('Check Response', page)
            soup = BeautifulSoup(response.content, 'html.parser')

            if (soup.find('ul', id='productList')):
                products_list = soup.find('ul', id='productList').find_all('li',
                                                                        class_='search-product')  # Added class attribute
            else:
                break

            for li in products_list:
                uuid = id + digital_list.get(keyword)
                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                image = li.find('img', class_='search-product-wrap-img').get('src')
                image_link = "https:"+image

                # base_price_element = li.find('del', class_='base-price')
                # base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

                re_price = re.sub(pattern, '', price)

                # Product 메시지
                product_message = {
                    "code": f"{uuid}{prd_name}",
                    "majorCategory": major_category,
                    "minorCategory": keyword,
                    "productName": prd_name,
                    "price": int(re_price),
                    "productLink": prd_link,
                    "productImage": image_link,
                    'provider' : '쿠팡'
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka(product_message, topic)

    ## 크롤링한 데이터 프레임 생성
    # df = pd.DataFrame(products_link)
    # print(df)
################################## 가구 키워드 크롤링 #####################################
def coupang_products_furniture():
    furniture_list = {"침대" : "1", "쇼파" : "2", "책상" : "3", "옷장" : "4"}

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
    }

    # 상위 카테고리 설정
    major_category = "가구"
    # Product Id 설정
    id = "2"
    # 토픽 설정
    topic = "furniture"
    for keyword in furniture_list:
        cnt = 1        
        for page in range(1, 10):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=true'
            print('check url ' + url)

            response = requests.get(url, headers=headers)
            time.sleep(0.5)

            print('Check Response', page)
            soup = BeautifulSoup(response.content, 'html.parser')

            if (soup.find('ul', id='productList')):
                products_list = soup.find('ul', id='productList').find_all('li',
                                                                        class_='search-product')  # Added class attribute
            else:
                break

            for li in products_list:
                uuid = id + furniture_list.get(keyword)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                image = li.find('img', class_='search-product-wrap-img').get('src')
                image_link = "https:"+image

                # base_price_element = li.find('del', class_='base-price')
                # base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

                re_price = re.sub(pattern, '', price)

                # Product 메시지
                product_message = {
                    "code": f"{uuid}{prd_name}",
                    "majorCategory": major_category,
                    "minorCategory": keyword,
                    "productName": prd_name,
                    "price": int(re_price),
                    "productLink": prd_link,
                    "productImage": image_link,
                    'provider' : '쿠팡'
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka(product_message, topic)
################################## 생활용품 키워드 크롤링 #####################################
def coupang_products_necessaries():
    necessaries_list = {"주방" : "1", "욕실" : "2", "청소" : "3", "수납": "4"}

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
    }

    # 상위 카테고리 설정
    major_category = "생활용품"
    # Product Id 설정
    id = "3"
    # 토픽 설정
    topic = "necessaries"
    for keyword in necessaries_list:
        cnt = 1        
        for page in range(1, 10):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=true'
            print('check url ' + url)

            response = requests.get(url, headers=headers)
            time.sleep(0.5)

            print('Check Response', page)
            soup = BeautifulSoup(response.content, 'html.parser')

            if (soup.find('ul', id='productList')):
                products_list = soup.find('ul', id='productList').find_all('li',
                                                                        class_='search-product')  # Added class attribute
            else:
                break

            for li in products_list:
                uuid = id + necessaries_list.get(keyword)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                image = li.find('img', class_='search-product-wrap-img').get('src')
                image_link = "https:"+image

                # base_price_element = li.find('del', class_='base-price')
                # base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

                re_price = re.sub(pattern, '', price)

                # Product 메시지
                product_message = {
                    "code": f"{uuid}{prd_name}",
                    "majorCategory": major_category,
                    "minorCategory": keyword,
                    "productName": prd_name,
                    "price": int(re_price),
                    "productLink": prd_link,
                    "productImage": image_link,
                    'provider' : '쿠팡'
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka(product_message, topic)
################################## 식품 키워드 크롤링 #####################################
def coupang_products_food():
    food_list = {"음료" : "1", "과일" : "2", "채소": "3", "과자": "4", "축산": "5", "가공식품": "6"}

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
    }

    # 상위 카테고리 설정
    major_category = "식품"
    # Product Id 설정
    id = "4"
    # 토픽 설정
    topic = "food"
    for keyword in food_list:
        cnt = 1        
        for page in range(1, 10):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=true'
            print('check url ' + url)

            response = requests.get(url, headers=headers)
            time.sleep(0.5)

            print('Check Response', page)
            soup = BeautifulSoup(response.content, 'html.parser')

            if (soup.find('ul', id='productList')):
                products_list = soup.find('ul', id='productList').find_all('li',
                                                                        class_='search-product')  # Added class attribute
            else:
                break

            for li in products_list:
                uuid = id + food_list.get(keyword)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                image = li.find('img', class_='search-product-wrap-img').get('src')
                image_link = "https:"+image

                # base_price_element = li.find('del', class_='base-price')
                # base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

                re_price = re.sub(pattern, '', price)

                # Product 메시지
                product_message = {
                    "code": f"{uuid}{prd_name}",
                    "majorCategory": major_category,
                    "minorCategory": keyword,
                    "productName": prd_name,
                    "price": int(re_price),
                    "productLink": prd_link,
                    "productImage": image_link,
                    'provider' : '쿠팡'
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka(product_message, topic)