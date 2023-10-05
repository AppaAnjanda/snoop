package com.appa.snoop.domain.usecase.home

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.repository.HomeRepository
import javax.inject.Inject

class GetHotKeywordListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): NetworkResult<List<String>> {
        return homeRepository.getHotKeywordList()
    }
}