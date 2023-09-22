package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.BaseModel
import kotlinx.coroutines.flow.Flow

// TODO("비지니스 로직을 처리하는 Repository Interface")
interface BaseRepository {
    suspend fun getAllList(): Flow<BaseModel>
}