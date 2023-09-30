package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.model.product.request.AlertPriceRequest
import com.appa.snoop.data.model.product.response.GraphItemResponse
import com.appa.snoop.data.model.product.response.TimingResponse
import com.appa.snoop.data.model.product.response.WishProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("api/product/{productCode}")
    suspend fun getProductDetail(
        @Path("productCode") productCode: String,
    ): ProductResponse

    @GET("api/product/timing/{productCode}")
    suspend fun getTiming(
        @Path("productCode") productCode: String,
    ): TimingResponse

    @GET("api/product/refresh/{productCode}")
    suspend fun refreshProduct(
        @Path("productCode") productCode: String,
    ): String

    @GET("api/product/recommend/{productCode}")
    suspend fun getRecommendProduct(
        @Path("productCode") productCode: String,
    ): List<ProductResponse>

    @GET("api/product/graph/{productCode}/{period}")
    suspend fun getProductGraph(
        @Path("productCode") productCode: String,
        @Path("period") period: String,
    ): List<GraphItemResponse>

    @POST("api/wishbox/add/alert/{productCode}")
    suspend fun registWishProduct(
        @Path("productCode") productCode: String,
        @Body alertPrice: AlertPriceRequest
    ): WishProductResponse
}