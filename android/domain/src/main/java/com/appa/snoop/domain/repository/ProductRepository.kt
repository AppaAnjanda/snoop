package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.product.Timing

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
}