package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductPagingResponse
import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.model.product.response.TimingResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("api/product/{productCode}")
    suspend fun getProductDetail(
        @Path("productCode") productCode: String,
    ) : ProductResponse

    @GET("api/product/timing/{productCode}")
    suspend fun getTiming(
        @Path("productCode") productCode: String,
    ) : TimingResponse

    @GET("api/product/refresh/{productCode}")
    suspend fun refreshProduct(
        @Path("productCode") productCode: String,
    ) : String

//    @GET("api/product/recommend/{productCode}")
//    suspend fun getRecommendProduct(
//        @Path("productCode") productCode: String,
//    ) : String

}