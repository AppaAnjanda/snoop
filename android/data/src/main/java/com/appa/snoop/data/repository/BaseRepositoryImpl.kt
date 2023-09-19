package com.appa.snoop.data.repository

import com.appa.snoop.data.repository.datasource.BaseRemoteDataSource
import com.appa.snoop.domain.model.BaseModel
import com.appa.snoop.domain.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

// TODO("Domain 비지니스 로직 Repository 구현 클래스")
class BaseRepositoryImpl(
    private val remoteDataSource: BaseRemoteDataSource
): BaseRepository {
    override suspend fun getAllList(): Flow<BaseModel> {
        TODO("Not yet implemented")
        
    }
}