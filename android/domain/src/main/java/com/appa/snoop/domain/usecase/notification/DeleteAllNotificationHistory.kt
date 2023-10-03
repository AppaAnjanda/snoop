package com.appa.snoop.domain.usecase.notification

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteAllNotificationHistory @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): NetworkResult<String> {
        return notificationRepository.deleteAllNotificationHistory()
    }
}