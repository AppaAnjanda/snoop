package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.model.product.request.AlertPriceRequest
import com.appa.snoop.data.model.wishbox.response.WishBoxDeleteResponse
import com.appa.snoop.data.model.wishbox.response.WishBoxResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WishBoxService {
    @GET("api/wishbox")
    suspend fun getWishBoxList(
    ): List<WishBoxResponse>

    @GET("api/wishbox/remove/{wishboxId}")
    suspend fun deleteWishBox(
        @Path("wishboxId") wishboxId: Int
    ): WishBoxDeleteResponse

    @POST("api/wishbox/update/{wishboxId}")
    suspend fun updateWishBoxPrice(
        @Path("wishboxId") wishboxId: Int,
        @Body alertPrice: AlertPriceRequest
    ): WishBoxResponse
}