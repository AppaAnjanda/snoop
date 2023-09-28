package com.appa.snoop.domain.usecase.category

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.repository.CategoryRepository
import com.appa.snoop.domain.repository.CategoryRepository.Companion.MAX_PRICE
import com.appa.snoop.domain.repository.CategoryRepository.Companion.MIN_PRICE
import javax.inject.Inject

class GetProductListByCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(
        majorName: String,
        minorName: String,
        pageNum: Int,
        minPrice: Int = MIN_PRICE,
        maxPrice: Int = MAX_PRICE
    ): NetworkResult<ProductPaging> {
        return categoryRepository.getProductListByCategory(majorName, minorName, pageNum, minPrice, maxPrice)
    }
}