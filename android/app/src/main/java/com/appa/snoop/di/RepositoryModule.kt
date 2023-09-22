package com.appa.snoop.di

import android.content.Context
import com.appa.snoop.data.local.PreferenceDataSource
import com.appa.snoop.data.repository.MemberRepositoryImpl
import com.appa.snoop.data.repository.RegisterRepositoryImpl
import com.appa.snoop.data.repository.datasource.BaseRemoteDataSource
import com.appa.snoop.data.repository.datasourceImpl.BaseRemoteDataSourceImpl
import com.appa.snoop.data.service.BaseService
import com.appa.snoop.data.service.MemberService
import com.appa.snoop.data.service.RegisterService
import com.appa.snoop.domain.repository.MemberRepository
import com.appa.snoop.domain.repository.RegisterRepository
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
    fun provideRegisterRepository(registerService: RegisterService, preferenceDataSource: PreferenceDataSource) : RegisterRepository {
        return RegisterRepositoryImpl(registerService = registerService, preferenceDatasource = preferenceDataSource)
    }

    @Provides
    @Singleton
    fun providePreferenceDataSource(@ApplicationContext context: Context) : PreferenceDataSource {
        return PreferenceDataSource(context)
    }
}