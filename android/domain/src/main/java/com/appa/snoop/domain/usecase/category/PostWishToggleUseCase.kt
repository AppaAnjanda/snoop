package com.appa.snoop.domain.usecase.category

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Wish
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class PostWishToggleUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(productCode: String): NetworkResult<Wish> {
        return categoryRepository.postWishToggle(productCode)
    }
}