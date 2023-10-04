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
import com.appa.snoop.data.R
import com.appa.snoop.data.local.PreferenceDataSource
import com.google.firebase.messaging.Constants
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

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        createNotificationChannel()
        var messageTitle = ""
        var messageBody = ""
        var productCode = ""
        var type = ""

        // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
        val notification = remoteMessage.notification
        val data = remoteMessage.data

        productCode = data.getValue(productCode).toString()
        Log.d(TAG, "onMessageReceived data: $data")
        messageTitle = notification?.title.toString()
        messageBody = notification?.body.toString()
        Log.d(TAG, "onMessageReceived notification: $messageTitle, $messageBody")

        val mainIntent = Intent(this, Class.forName("com.appa.snoop.presentation.MainActivity")).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("productCode", productCode)
        }

        val mainPendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 101, mainIntent,PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        val summaryNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setContentIntent(mainPendingIntent)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .setFullScreenIntent(mainPendingIntent, true)

        NotificationManagerCompat.from(this).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
            }
            notify(101, summaryNotification.build())
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}

// Notification
const val CHANNEL_ID = "snoop_channel"
const val CHANNEL_NAME = "기웃기웃"