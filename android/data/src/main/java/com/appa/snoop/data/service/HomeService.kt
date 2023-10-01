package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.domain.model.category.ProductPaging
import retrofit2.http.GET

interface HomeService {
    // 디지털가전 인기 상품 조회
    @GET("api/home/hotProduct/digital")
    suspend fun getPopularDigitalList() : List<ProductResponse>

    // 생활용품 인기 상품 조회
    @GET("api/home/hotProduct/necessaries")
    suspend fun getPopularNecessariesList() : List<ProductResponse>

    // 가구 인기 상품 조회
    @GET("api/home/hotProduct/furniture")
    suspend fun getPopularFurnitureList() : List<ProductResponse>

    // 식품 인기 상품 조회
    @GET("api/home/hotProduct/food")
    suspend fun getPopularFoodList() : List<ProductResponse>

    // 찜 기반 상품 추천
    @GET("api/home/recommend/byWishbox")
    suspend fun getRecommendProductList() : List<ProductResponse>

    // 인기 검색어 조회
    @GET("api/home/hotKeyword")
    suspend fun getHotKeywordList() : List<String>
}