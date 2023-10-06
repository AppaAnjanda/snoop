package com.appa.snoop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BaseEntity(
    @PrimaryKey
    val base: Int
)