package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.model.category.Wish
import com.appa.snoop.domain.model.member.JwtTokens

interface CategoryRepository {
    // 상품 카테고리로 조회
    suspend fun getProductListByCategory(majorName: String, minorName: String, pageNum: Int): NetworkResult<ProductPaging>

    // 상품 키워드로 조회
    suspend fun getProductListByKeyword(keyword: String, pageNum: Int): NetworkResult<ProductPaging>

    // 위시리스트 토글
    suspend fun postWishToggle(productCode: String): NetworkResult<Wish>
}