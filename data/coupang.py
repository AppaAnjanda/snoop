import requests
from bs4 import BeautifulSoup
import pandas as pd
import time
import json
from kafka_producer import send_to_kafka2
from datetime import datetime

digital_list = {"TV" : "1", "냉장고" : "2", "세탁기": "3", "청소기": "4", "노트북": "5", "데스크탑": "6", "키보드": "7", "마우스": "8", "모니터": "9"}
furniture_list = {"침대" : "1", "쇼파" : "2", "책상" : "3", "옷장" : "4"}
necessaries_list = {"주방" : "1", "욕실" : "2", "청소" : "3", "수납": "4"}
food_list = {"음료" : "1", "과일" : "2", "채소": "3", "과자": "4", "축산": "5", "가공식품": "6"}

################################## 단일 키워드 크롤링 #####################################
def coupang_products(keyword, pages):
    digital_list = ["TV", "냉장고", "세탁기", "청소기", "노트북", "데스크탑", "키보드", "마우스", "모니터"]
    furniture_list = ["침대", "쇼파", "책상", "옷장"]

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
    }

    products_link = []
    print('Start Crawler')
    for page in range(1, pages + 1):
        url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=false'
        print('check url ' + url)

        response = requests.get(url, headers=headers)
        time.sleep(0.3)

        print('Check Response', page)
        soup = BeautifulSoup(response.content, 'html.parser')

        if (soup.find('ul', id='productList')):
            products_list = soup.find('ul', id='productList').find_all('li',
                                                                       class_='search-product')  # Added class attribute
        else:
            break

        for li in products_list:
            a_link = li.find('a', href=True)['href']
            prd_link = BASE_URL + a_link
            prd_name = li.find('div', class_='name').text.strip()

            base_price_element = li.find('del', class_='base-price')
            base_price = base_price_element.text if base_price_element else ''

            price_element = li.find('strong', class_='price-value')
            price = price_element.text if price_element else ''

            products_info = {
                'name': prd_name,
                'base_price': base_price,
                'price': price,
                'product_url': prd_link,
                'provider': 'coupang',
                'major_category' : '디지털/가전',
                'minor_category' : '노트북',
                'provider' : '쿠팡'
            }
        
            send_to_kafka(products_info) # Kafka에 전송
            products_link.append(products_info)

    print(len(products_link))
    ## 크롤링한 데이터 프레임 생성
    df = pd.DataFrame(products_link)
    print(df)
    # df.to_csv('Coupang_Laptop_Crawling.csv', index=False, encoding='utf-8-sig')

################################## 전체 키워드 크롤링 #####################################
def coupang_products_all():
    digital_list = ["TV", "냉장고", "세탁기", "청소기", "노트북", "데스크탑", "키보드", "마우스", "모니터"]
    furniture_list = ["침대", "쇼파", "책상", "옷장"]

    all_list = digital_list + furniture_list

    BASE_URL = 'https://www.coupang.com'  # Corrected URL format
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36',
        'Accept-Language': 'ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3'
    }

    products_link = []
    print('Start Crawler')
    for keyword in all_list:

        # 상위 카테고리 설정
        major_category = ""
        if keyword in digital_list:
            major_category = "디지털가전"
        elif keyword in furniture_list:
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
        
        for page in range(1, 27):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=false'
            print('check url ' + url)

            response = requests.get(url, headers=headers)
            time.sleep(1)

            print('Check Response', page)
            soup = BeautifulSoup(response.content, 'html.parser')

            if (soup.find('ul', id='productList')):
                products_list = soup.find('ul', id='productList').find_all('li',
                                                                        class_='search-product')  # Added class attribute
            else:
                break

            for li in products_list:
                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                base_price_element = li.find('del', class_='base-price')
                base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')

                # Product 메시지
                product_message = {
                    "id": f"product_{id}",
                    "major_category": major_category,
                    "minor_category": keyword,
                    "product_name": prd_name,
                    "index_name": major_category,
                    "product_link": prd_link,
                    "product_image": "http://example.com/images/ex.jpg",
                    'provider' : '쿠팡',
                    "last_update" : current_time
                    }

                # Price 메시지
                price_message = {
                    "id": f"price_{id}",
                    "routing": f"product_{id}",
                    "index_name": major_category,
                    "timestamp": current_time,
                    "price": price
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka2(product_message, price_message, major_category)
                products_link.append(products_info)

        print(len(products_link))
        
    ## 크롤링한 데이터 프레임 생성
    # df = pd.DataFrame(products_link)
    # print(df)

# df = coupang_products('노트북', 1)

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
        for page in range(1, 27):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=false'
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
                uuid = id + digital_list.get(keyword) + str(cnt)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                base_price_element = li.find('del', class_='base-price')
                base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')
                
                price_entry = {
                    "timestamp": current_time,
                    "price": price
                }

                # Product 메시지
                product_message = {
                    "id": f"product_{uuid}",
                    "major_category": major_category,
                    "minor_category": keyword,
                    "product_name": prd_name,
                    "index_name": major_category,
                    "product_link": prd_link,
                    "product_image": "http://example.com/images/ex.jpg",
                    'provider' : '쿠팡',
                    "last_update" : current_time
                }

                # Price 메시지
                price_message = {
                    "id": f"price_{uuid}",
                    "routing": f"product_{uuid}",
                    "index_name": major_category,
                    "price_history": [price_entry],
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka2(product_message, price_message, topic)
                cnt += 1

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
        for page in range(1, 27):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=false'
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
                uuid = id + furniture_list.get(keyword) + str(cnt)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                base_price_element = li.find('del', class_='base-price')
                base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')
                
                price_entry = {
                    "timestamp": current_time,
                    "price": price
                }

                # Product 메시지
                product_message = {
                    "id": f"product_{uuid}",
                    "major_category": major_category,
                    "minor_category": keyword,
                    "product_name": prd_name,
                    "index_name": major_category,
                    "product_link": prd_link,
                    "product_image": "http://example.com/images/ex.jpg",
                    'provider' : '쿠팡',
                    "last_update" : current_time
                }

                # Price 메시지
                price_message = {
                    "id": f"price_{uuid}",
                    "routing": f"product_{uuid}",
                    "index_name": major_category,
                    "price_history": [price_entry],
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka2(product_message, price_message, topic)
                cnt += 1
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
        for page in range(1, 27):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=false'
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
                uuid = id + furniture_list.get(keyword) + str(cnt)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                base_price_element = li.find('del', class_='base-price')
                base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')
                
                price_entry = {
                    "timestamp": current_time,
                    "price": price
                }

                # Product 메시지
                product_message = {
                    "id": f"product_{uuid}",
                    "major_category": major_category,
                    "minor_category": keyword,
                    "product_name": prd_name,
                    "index_name": major_category,
                    "product_link": prd_link,
                    "product_image": "http://example.com/images/ex.jpg",
                    'provider' : '쿠팡',
                    "last_update" : current_time
                }

                # Price 메시지
                price_message = {
                    "id": f"price_{uuid}",
                    "routing": f"product_{uuid}",
                    "index_name": major_category,
                    "price_history": [price_entry],
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka2(product_message, price_message, topic)
                cnt += 1
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
        for page in range(1, 27):
            url = f'https://www.coupang.com/np/search?q={keyword}&channel=user&sorter=scoreDesc&listSize=36&filter=&isPriceRange=false&rating=0&page={page}&rocketAll=false'
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
                uuid = id + furniture_list.get(keyword) + str(cnt)

                a_link = li.find('a', href=True)['href']
                prd_link = BASE_URL + a_link
                prd_name = li.find('div', class_='name').text.strip()

                base_price_element = li.find('del', class_='base-price')
                base_price = base_price_element.text if base_price_element else ''

                price_element = li.find('strong', class_='price-value')
                price = price_element.text if price_element else ''
                current_time = datetime.utcnow().strftime('%Y-%m-%d %H:%M:%S')
                
                price_entry = {
                    "timestamp": current_time,
                    "price": price
                }

                # Product 메시지
                product_message = {
                    "id": f"product_{uuid}",
                    "major_category": major_category,
                    "minor_category": keyword,
                    "product_name": prd_name,
                    "index_name": major_category,
                    "product_link": prd_link,
                    "product_image": "http://example.com/images/ex.jpg",
                    'provider' : '쿠팡',
                    "last_update" : current_time
                }

                # Price 메시지
                price_message = {
                    "id": f"price_{uuid}",
                    "routing": f"product_{uuid}",
                    "index_name": major_category,
                    "price_history": [price_entry],
                }

                # send_to_kafka(products_info) # Kafka에 전송
                send_to_kafka2(product_message, price_message, topic)
                cnt += 1