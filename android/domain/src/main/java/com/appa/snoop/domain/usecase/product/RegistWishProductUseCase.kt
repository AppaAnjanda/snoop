package com.appa.snoop.domain.usecase.product

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.product.WishProduct
import com.appa.snoop.domain.repository.ProductRepository
import javax.inject.Inject

class RegistWishProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        productCode: String,
        alertPrice: AlertPrice
    ): NetworkResult<WishProduct> {
        return productRepository.registWishProduct(productCode, alertPrice)
    }
}