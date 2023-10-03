package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.service.NotificationService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.notification.Notification
import com.appa.snoop.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRepository {
    override suspend fun getNotificationHistory(): NetworkResult<List<Notification>> {
        return handleApi {
            notificationService.getNotificationHistory().map { it.toDomain() }
        }
    }

    override suspend fun deleteNotificationHistory(alertId: Int): NetworkResult<String> {
        return handleApi {
            notificationService.deleteNotificationHistory(alertId = alertId)
        }
    }

    override suspend fun deleteAllNotificationHistory(): NetworkResult<String> {
        return handleApi {
            notificationService.deleteAllNotificationHistory()
        }
    }
}