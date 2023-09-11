package com.appa.snoop.data.repository.datasource

import com.appa.snoop.domain.model.BaseModel
import kotlinx.coroutines.flow.Flow

// TODO("데이터 가져오를 Remote or Local interface")
interface BaseRemoteDataSource {
    suspend fun getAllList(): Flow<BaseModel>
}