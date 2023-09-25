package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductPagingResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {
    @GET("api/search/{major}/{minor}/{page}")
    suspend fun getProductListByCategory(
        @Path("major") majorName: String,
        @Path("minor") minorName: String,
        @Path("page") page: Int
    ) : ProductPagingResponse

    @GET("api/search/{keyword}/{page}")
    suspend fun getProductListByKeyword(
        @Path("keyword") keyword: String,
        @Path("page") page: Int
    ) : ProductPagingResponse
}