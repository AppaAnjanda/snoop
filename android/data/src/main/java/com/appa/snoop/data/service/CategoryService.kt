package com.appa.snoop.data.service

import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.model.registration.response.RegisterResponse
import com.appa.snoop.domain.model.member.Register
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {
    @GET("api/search/{major}/{minor}")
    suspend fun getProductListByCategory(
        @Path("major") majorName: String,
        @Path("minor") minorName: String
    ) : MutableList<ProductResponse>
}