package com.appa.snoop.domain.usecase.category

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class GetProductListByKeywordUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(keyword: String, pageNum: Int) : NetworkResult<ProductPaging> {
        return categoryRepository.getProductListByKeyword(keyword, pageNum)
    }
}