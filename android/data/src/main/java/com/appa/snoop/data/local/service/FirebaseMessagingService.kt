package com.appa.snoop.data.local.service

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "[김진영] FirebaseMessaging"
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.notification != null) { // notification이 있는 경우 foreground처리
            var data = message.notification

            Log.d(TAG, "onMessageReceived(foreground): $data")
        } else { // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
            var data = message.data
            Log.d(TAG, "onMessageReceived(background): $data")
        }
    }
}