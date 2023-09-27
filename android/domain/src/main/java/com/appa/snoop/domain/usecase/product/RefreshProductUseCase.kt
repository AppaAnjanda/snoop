package com.appa.snoop.domain.usecase.product

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.Timing
import com.appa.snoop.domain.repository.ProductRepository
import javax.inject.Inject

class RefreshProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productCode: String): NetworkResult<String> {
        return productRepository.refreshProduct(productCode)
    }
}