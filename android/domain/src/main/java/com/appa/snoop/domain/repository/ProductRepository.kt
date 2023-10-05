package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.product.GraphItem
import com.appa.snoop.domain.model.product.Timing
import com.appa.snoop.domain.model.product.WishProduct

interface ProductRepository {
    suspend fun getProductDetail(
        productCode: String,
    ): NetworkResult<Product>

    suspend fun getTiming(
        productCode: String,
    ): NetworkResult<Timing>

    suspend fun refreshProduct(
        productCode: String,
    ): NetworkResult<String>

    suspend fun getRecommendProduct(
        productCode: String,
    ): NetworkResult<List<Product>>

    suspend fun getProductGraph(
        productCode: String,
        period: String,
    ): NetworkResult<List<GraphItem>>

    suspend fun registWishProduct(
        productCode: String,
        alertPrice: AlertPrice
    ): NetworkResult<WishProduct>

}