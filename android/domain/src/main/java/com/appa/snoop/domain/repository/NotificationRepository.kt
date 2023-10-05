package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.notification.Notification

interface NotificationRepository {
    suspend fun getNotificationHistory(
    ): NetworkResult<List<Notification>>

    suspend fun deleteNotificationHistory(
        alertId: Int
    ): NetworkResult<String>

    suspend fun deleteAllNotificationHistory(
    ): NetworkResult<String>
}