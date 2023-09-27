package com.appa.snoop.data.local.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.appa.snoop.data.local.PreferenceDataSource
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

private const val TAG = "[김진영] FirebaseMessaging"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
//        val CHANNEL_ID = "1"
//        super.onMessageReceived(message)
        if (message.notification != null) { // notification이 있는 경우 foreground처리
            var data = message.notification

            Log.d(TAG, "onMessageReceived(foreground): ${data?.title} ${data?.body}")
        } else { // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
            var data = message.data
            Log.d(TAG, "onMessageReceived(background): $data")
        }

//        val mainIntent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }

//        val mainPendingIntent: PendingIntent =
//            PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)

//        var builder: NotificationCompat.Builder
//        var notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(
//                CHANNEL_ID,
//                "hifes notification",
//                NotificationManager.IMPORTANCE_HIGH
//            )
//            notificationManager.createNotificationChannel(notificationChannel)
//            builder = NotificationCompat.Builder(this@FirebaseMessagingService, CHANNEL_ID)
//        } else {
//            builder = NotificationCompat.Builder(this@FirebaseMessagingService)
//        }

//        builder.setSmallIcon(R.drawable.logo_background_hifes)
//            .setContentTitle(messageTitle)
//            .setContentText(messageContent)
//            .setAutoCancel(true)
//            .setContentIntent(mainPendingIntent)
//
//        NotificationManagerCompat.from(this).apply {
//            if (ActivityCompat.checkSelfPermission(
//                    this@FirebaseMessagingService,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                return
//            }
//            notify(101, builder.build())
//        }
    }
}