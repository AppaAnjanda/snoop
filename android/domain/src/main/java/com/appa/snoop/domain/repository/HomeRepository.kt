package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product

interface HomeRepository {
    // 디지털가전 인기 상품 조회
    suspend fun getPopularDigitalList() : NetworkResult<List<Product>>

    // 생활용품 인기 상품 조회
    suspend fun getPopularNecessariesList() : NetworkResult<List<Product>>

    // 가구 인기 상품 조회
    suspend fun getPopularFurnitureList() : NetworkResult<List<Product>>

    // 식품 인기 상품 조회
    suspend fun getPopularFoodList() : NetworkResult<List<Product>>

    // 찜 기반 상품 추천
    suspend fun getRecommendProductList() : NetworkResult<List<Product>>

    // 인기 검색어 조회
    suspend fun getHotKeywordList() : NetworkResult<List<String>>
}