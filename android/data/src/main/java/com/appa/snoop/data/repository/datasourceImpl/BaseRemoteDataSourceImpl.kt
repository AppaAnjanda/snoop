package com.appa.snoop.data.repository.datasourceImpl

import com.appa.snoop.data.remote.BaseService
import com.appa.snoop.data.repository.datasource.BaseRemoteDataSource
import com.appa.snoop.domain.model.BaseModel
import kotlinx.coroutines.flow.Flow

// TODO("datasource 구현 클래스")
class BaseRemoteDataSourceImpl(
    private val service: BaseService
): BaseRemoteDataSource {
    override suspend fun getAllList(): Flow<BaseModel> {
        TODO("Not yet implemented")
    }
}