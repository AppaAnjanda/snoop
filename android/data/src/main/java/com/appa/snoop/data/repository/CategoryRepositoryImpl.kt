package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.mapper.toDto
import com.appa.snoop.data.service.CategoryService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.model.category.Wish
import com.appa.snoop.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryRepository {

    override suspend fun getProductListByCategory(
        majorName: String,
        minorName: String,
        pageNum: Int,
        minPrice: Int,
        maxPrice: Int
    ): NetworkResult<ProductPaging> {
        return handleApi { categoryService.getProductListByCategory(majorName, minorName, pageNum, minPrice, maxPrice).toDto() }
    }

    override suspend fun getProductListByKeyword(
        keyword: String,
        pageNum: Int,
        minPrice: Int,
        maxPrice: Int
    ): NetworkResult<ProductPaging> {
        return handleApi { categoryService.getProductListByKeyword(keyword, pageNum, minPrice, maxPrice).toDto() }
    }

    override suspend fun postWishToggle(
        productCode: String
    ): NetworkResult<Wish> {
        return handleApi { categoryService.postWishToggle(productCode).toDomain() }
    }

    override suspend fun getSearchHistory(
    ): NetworkResult<List<String>> {
        return handleApi { categoryService.getSearchHistory() }
    }

    override suspend fun deleteSearchHistory(
        keyword: String
    ): NetworkResult<String> {
        return handleApi { categoryService.deleteSearchHistory(keyword) }
    }
}