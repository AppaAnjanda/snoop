package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.service.ProductService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.product.Timing
import com.appa.snoop.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
) : ProductRepository {
    override suspend fun getProductDetail(productCode: String): NetworkResult<Product> {
        return handleApi {
            productService.getProductDetail(productCode).toDomain()
        }
    }

    override suspend fun getTiming(productCode: String): NetworkResult<Timing> {
        return handleApi {
            productService.getTiming(productCode).toDomain()
        }
    }

    override suspend fun refreshProduct(productCode: String): NetworkResult<String> {
        return handleApi {
            productService.refreshProduct(productCode)
        }
    }

}