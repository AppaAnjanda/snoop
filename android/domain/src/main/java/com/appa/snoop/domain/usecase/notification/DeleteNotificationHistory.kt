package com.appa.snoop.domain.usecase.notification

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.notification.Notification
import com.appa.snoop.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationHistory @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(alertId: Int): NetworkResult<String> {
        return notificationRepository.deleteNotificationHistory(alertId = alertId)
    }
}