package com.appa.snoop.domain.usecase.product

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.GraphItem
import com.appa.snoop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductGraphUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productCode: String, period: String): NetworkResult<List<GraphItem>> {
        return productRepository.getProductGraph(productCode, period)
    }
}