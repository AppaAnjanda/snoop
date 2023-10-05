package com.appa.snoop.di

import com.appa.snoop.BuildConfig.BASE_URL
import com.appa.snoop.data.service.BaseService
import com.appa.snoop.data.service.CategoryService
import com.appa.snoop.data.service.MemberService
import com.appa.snoop.data.service.RegisterService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.appa.snoop.data.interceptor.RequestInterceptor
import com.appa.snoop.data.interceptor.ResponseInterceptor
import com.appa.snoop.data.service.ChatService
import com.appa.snoop.data.service.HomeService
import com.appa.snoop.data.service.NotificationService
import com.appa.snoop.data.service.ProductService
import com.appa.snoop.data.service.WishBoxService
import com.appa.snoop.di.NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("BASE_URL")
    fun BaseUrl() : String = BASE_URL

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
                    .setLenient()
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

    @Provides
    @Singleton
    fun provideHomeService(
        retrofit: Retrofit
    ): HomeService = retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideProductService(
        retrofit: Retrofit
    ): ProductService = retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideWishBoxService(
        retrofit: Retrofit
    ): WishBoxService = retrofit.create(WishBoxService::class.java)

    @Provides
    @Singleton
    fun provideChatService(
        retrofit: Retrofit
    ): ChatService = retrofit.create(ChatService::class.java)

    @Provides
    @Singleton
    fun provideNotificationService(
        retrofit: Retrofit
    ): NotificationService = retrofit.create(NotificationService::class.java)
}