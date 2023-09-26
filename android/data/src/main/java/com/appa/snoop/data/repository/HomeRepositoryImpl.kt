package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDto
import com.appa.snoop.data.service.HomeService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun getPopularDigitalList(): NetworkResult<List<Product>> {
        return handleApi { homeService.getPopularDigitalList().map{ it.toDto() } }
    }

    override suspend fun getPopularNecessariesList(): NetworkResult<List<Product>> {
        return handleApi { homeService.getPopularNecessariesList().map{ it.toDto() } }
    }

    override suspend fun getPopularFurnitureList(): NetworkResult<List<Product>> {
        return handleApi { homeService.getPopularFurnitureList().map{ it.toDto() } }
    }

    override suspend fun getPopularFoodList(): NetworkResult<List<Product>> {
        return handleApi { homeService.getPopularFoodList().map{ it.toDto() } }
    }

}