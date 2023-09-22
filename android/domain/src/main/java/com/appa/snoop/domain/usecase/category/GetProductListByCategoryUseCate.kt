package com.appa.snoop.domain.usecase.category

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class GetProductListByCategoryUseCate @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(majorName: String, minorName: String): NetworkResult<List<Product>> {
        return categoryRepository.getProductListByCategory(majorName, minorName)
    }
}