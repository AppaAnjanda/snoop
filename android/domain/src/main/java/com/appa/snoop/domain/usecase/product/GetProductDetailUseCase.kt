package com.appa.snoop.domain.usecase.product

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productCode: String): NetworkResult<Product> {
        return productRepository.getProductDetail(productCode = productCode)
    }
}