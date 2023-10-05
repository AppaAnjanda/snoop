package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.model.category.Wish
import com.appa.snoop.domain.model.member.JwtTokens

interface CategoryRepository {
    companion object {
        const val MIN_PRICE = 0
        const val MAX_PRICE = 99999999
    }

    // 상품 카테고리로 조회
    suspend fun getProductListByCategory(
        majorName: String,
        minorName: String,
        pageNum: Int,
        minPrice: Int = MIN_PRICE,
        maxPrice: Int = MAX_PRICE
    ): NetworkResult<ProductPaging>

    // 상품 키워드로 조회
    suspend fun getProductListByKeyword(
        keyword: String,
        pageNum: Int,
        minPrice: Int = MIN_PRICE,
        maxPrice: Int = MAX_PRICE
    ): NetworkResult<ProductPaging>

    // 위시리스트 토글
    suspend fun postWishToggle(productCode: String): NetworkResult<Wish>

    // 검색기록 조회
    suspend fun getSearchHistory(
    ): NetworkResult<List<String>>

    // 검색기록 삭제
    suspend fun deleteSearchHistory(
        keyword: String
    ): NetworkResult<String>
}