package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.notification.response.NotificationResponse
import com.appa.snoop.domain.model.notification.Notification

fun NotificationResponse.toDomain(): Notification {
    return Notification(
        body = body,
        createTime = createTime,
        id = id,
        title = title,
        imageUrl = imageUrl,
        productCode = productCode
    )
}