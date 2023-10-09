package com.appa.snoop.domain.usecase.home

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.repository.HomeRepository
import javax.inject.Inject

class GetPopularDigitalListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() : NetworkResult<List<Product>> {
        return homeRepository.getPopularDigitalList()
    }
}