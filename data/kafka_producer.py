import json
from kafka import KafkaProducer
from datetime import datetime
import time
import re

def on_send_success(record_metadata):
    print(f"Record sent to topic {record_metadata.topic} partition [{record_metadata.partition}] offset [{record_metadata.offset}]")

def on_send_error(excp):
    print(f"I am an errback: {excp}")

# def send_to_kafka(products_info):
#     # Kafka 프로듀서 설정
#     producer_config = {
#         'bootstrap.servers': 'j9d104a.p.ssafy.io:9092',  # Kafka 브로커의 주소
#     }

#     producer = Producer(producer_config)

#     # Product Id 설정
#     id = 0
#     if products_info.get("major_category") == "디지털가전":
#         id = 1
#     elif products_info.get("major_category") == "가구":
#         id = 2
#     elif products_info.get("major_category") == "생활용품":
#         id = 3
#     else:
#         id = 4

#     # Kafka 토픽 설정
#     kafka_topic = products_info.get("major_category")  # 보내려는 Kafka 토픽 이름
    
#     try:
#         # 데이터를 JSON 형식으로 직렬화하여 Kafka 토픽으로 보냄
#         producer.produce(kafka_topic, key=None, value=json.dumps(products_info, ensure_ascii=False).encode('utf-8'))

#         # 메시지 전송 확인
#         producer.poll(0)
#         producer.flush()

#     except KafkaException as e:
#         if e.args[0].code() == KafkaError._INVALID_ARG:
#             print(f"Invalid argument: {e}")
#         else:
#             print(f"Failed to send message to Kafka: {e}")
#     except Exception as e:
#         print(f"An error occurred: {e}")
#     finally:
#         producer.flush()
#         # producer.close()

def send_to_kafka(product_message, topic):
    producer = KafkaProducer(
        bootstrap_servers=['j9d104a.p.ssafy.io:9092'],
        value_serializer=lambda v: json.dumps(v).encode('utf-8')
    )

    producer.send(topic, value=product_message).add_callback(on_send_success).add_errback(on_send_error)

    producer.flush()
