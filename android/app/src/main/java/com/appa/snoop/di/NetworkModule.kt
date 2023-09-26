package com.appa.snoop.di

import com.appa.snoop.data.service.BaseService
import com.appa.snoop.data.service.CategoryService
import com.appa.snoop.data.service.MemberService
import com.appa.snoop.data.service.RegisterService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.appa.snoop.data.interceptor.RequestInterceptor
import com.appa.snoop.data.interceptor.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("BASE_URL")
    fun BaseUrl() : String = "http://52.78.159.20:8080/"

    @Singleton
    @Provides
    fun provideOkHttpClient(
        requestInterceptor: RequestInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(requestInterceptor) // 헤더 JWT 통신
        .addInterceptor(responseInterceptor) // 리퀘스트 코드 invalid check
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
//    }


    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("BASE_URL") baseUrl: String,
        requestInterceptor: RequestInterceptor,
        responseInterceptor: ResponseInterceptor
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            )
        )
        .client(provideOkHttpClient(requestInterceptor, responseInterceptor))
        .build()

    @Singleton
    @Provides
    fun provideBaseService(
        retrofit: Retrofit
    ): BaseService = retrofit.create(BaseService::class.java)

    @Provides
    @Singleton
    fun provideRegisterService(
        retrofit: Retrofit
    ): RegisterService = retrofit.create(RegisterService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(
        retrofit: Retrofit
    ): CategoryService = retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideMemberService(
        retrofit: Retrofit
    ): MemberService = retrofit.create(MemberService::class.java)
}