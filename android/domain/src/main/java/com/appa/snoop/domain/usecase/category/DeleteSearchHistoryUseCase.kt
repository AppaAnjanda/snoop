package com.appa.snoop.domain.usecase.category

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(keyword: String): NetworkResult<String> {
        return categoryRepository.deleteSearchHistory(keyword)
    }
}