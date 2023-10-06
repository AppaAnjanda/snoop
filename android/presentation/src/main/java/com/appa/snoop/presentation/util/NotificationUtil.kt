package com.appa.snoop.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

// Android Oreo 이상에서는 알림 채널을 생성해야 한다
@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(notificationManager: NotificationManager, id: String, name: String) {
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(id, name, importance)
    notificationManager.createNotificationChannel(channel)
}
