package com.appa.snoop.domain.usecase.category

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): NetworkResult<List<String>> {
        return categoryRepository.getSearchHistory()
    }
}