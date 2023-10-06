package com.appa.snoop.presentation

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.appa.snoop.presentation.ui.main.MainScreen
import com.appa.snoop.presentation.ui.theme.SnoopTheme
import com.appa.snoop.presentation.util.createNotificationChannel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint

// Notification
const val CHANNEL_ID = "snoop_channel"
const val CHANNEL_NAME = "기웃기웃"

private const val TAG = "MainActivity_김진영"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    private val productCode by lazy { intent.getStringExtra("productCode") }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(notificationManager, CHANNEL_ID, CHANNEL_NAME)
        Log.d(TAG, "onCreate: $productCode")
        // ime 동작 구현하기 위함
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SnoopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(productCode = productCode)
                }
            }
        }
    }
}