from difflib import SequenceMatcher
from konlpy.tag import Okt
from sentence_transformers import SentenceTransformer, util

sentence1 = "삼성전자 갤럭시탭 A8 SM-X20"
sentence2 = "삼성전자 A8 SM-X200 갤럭시탭"

################################## SequenceMatcher #####################################

def similarity(a, b):
    return SequenceMatcher(None, a, b).ratio()

similarity_score = similarity(sentence1, sentence2)

################################## konlpy #####################################

okt = Okt()
tokens1 = okt.nouns(sentence1)
tokens2 = okt.nouns(sentence2)

common_tokens = set(tokens1) & set(tokens2)

similarity_konlpy = len(common_tokens) / (len(set(tokens1)) + len(set(tokens2)) - len(common_tokens))

################################## sentence transformers #####################################

model = SentenceTransformer("paraphrase-MiniLM-L6-v2")
embeddings1 = model.encode(sentence1, convert_to_tensor=True)
embeddings2 = model.encode(sentence2, convert_to_tensor=True)

similarity_sentence_transformers = util.pytorch_cos_sim(embeddings1, embeddings2)

print("SequenceMatcher result : ",similarity_score)
print("konlpy result : ",similarity_konlpy)
print("sentence_transformers : ",similarity_sentence_transformers)