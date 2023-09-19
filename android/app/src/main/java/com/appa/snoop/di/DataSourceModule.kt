package com.appa.snoop.di

import com.appa.snoop.data.repository.datasource.BaseRemoteDataSource
import com.appa.snoop.data.repository.datasourceImpl.BaseRemoteDataSourceImpl
import com.appa.snoop.data.service.BaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideBaseRepository(baseService: BaseService): BaseRemoteDataSource {
        return BaseRemoteDataSourceImpl(baseService)
    }
}