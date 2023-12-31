package com.appa.snoop.di

import android.content.Context
import com.appa.snoop.data.local.PreferenceDataSource
import com.appa.snoop.data.repository.CategoryRepositoryImpl
import com.appa.snoop.data.repository.ChatRepositoryImpl
import com.appa.snoop.data.repository.HomeRepositoryImpl
import com.appa.snoop.data.repository.MemberRepositoryImpl
import com.appa.snoop.data.repository.NotificationRepositoryImpl
import com.appa.snoop.data.repository.ProductRepositoryImpl
import com.appa.snoop.data.repository.RegisterRepositoryImpl
import com.appa.snoop.data.repository.WishBoxRepositoryImpl
import com.appa.snoop.data.repository.datasource.BaseRemoteDataSource
import com.appa.snoop.data.repository.datasourceImpl.BaseRemoteDataSourceImpl
import com.appa.snoop.data.service.BaseService
import com.appa.snoop.data.service.CategoryService
import com.appa.snoop.data.service.ChatService
import com.appa.snoop.data.service.HomeService
import com.appa.snoop.data.service.MemberService
import com.appa.snoop.data.service.NotificationService
import com.appa.snoop.data.service.ProductService
import com.appa.snoop.data.service.RegisterService
import com.appa.snoop.data.service.WishBoxService
import com.appa.snoop.domain.repository.CategoryRepository
import com.appa.snoop.domain.repository.ChatRepository
import com.appa.snoop.domain.repository.HomeRepository
import com.appa.snoop.domain.repository.MemberRepository
import com.appa.snoop.domain.repository.NotificationRepository
import com.appa.snoop.domain.repository.ProductRepository
import com.appa.snoop.domain.repository.RegisterRepository
import com.appa.snoop.domain.repository.WishBoxRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideBaseRepository(baseService: BaseService): BaseRemoteDataSource {
        return BaseRemoteDataSourceImpl(baseService)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(
        registerService: RegisterService,
        preferenceDataSource: PreferenceDataSource
    ): RegisterRepository {
        return RegisterRepositoryImpl(
            registerService = registerService,
            preferenceDatasource = preferenceDataSource
        )
    }

    @Provides
    @Singleton
    fun providePreferenceDataSource(@ApplicationContext context: Context): PreferenceDataSource {
        return PreferenceDataSource(context)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryService: CategoryService): CategoryRepository {
        return CategoryRepositoryImpl(categoryService = categoryService)
    }

    @Provides
    @Singleton
    fun provideMemberRepository(memberService: MemberService): MemberRepository {
        return MemberRepositoryImpl(memberService = memberService)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(homeService: HomeService): HomeRepository {
        return HomeRepositoryImpl(homeService = homeService)
    }

    @Provides
    @Singleton
    fun provideProdcutRepository(productService: ProductService): ProductRepository {
        return ProductRepositoryImpl(productService = productService)

    }

    @Provides
    @Singleton
    fun provideWishBoxRepository(wishBoxService: WishBoxService): WishBoxRepository {
        return WishBoxRepositoryImpl(wishBoxService = wishBoxService)
    }

    @Provides
    @Singleton
    fun provideChatRepository(chatService: ChatService): ChatRepository {
        return ChatRepositoryImpl(chatService = chatService)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationService: NotificationService): NotificationRepository {
        return NotificationRepositoryImpl(notificationService = notificationService)
    }
}