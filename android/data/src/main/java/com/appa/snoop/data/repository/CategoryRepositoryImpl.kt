package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDto
import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.service.CategoryService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryRepository {
    override suspend fun getProductListByCategory(
        majorName: String,
        minorName: String
    ): NetworkResult<List<Product>> {
        return handleApi { categoryService.getProductListByCategory(majorName, minorName).map{ it.toDto() } }
    }
}