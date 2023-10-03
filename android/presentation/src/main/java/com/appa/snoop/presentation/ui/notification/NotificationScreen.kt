package com.appa.snoop.presentation.ui.notification

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.ui.notification.component.NotificationItem
import com.appa.snoop.presentation.ui.notification.viewmodel.NotificationViewModel
import com.appa.snoop.presentation.util.effects.NotificationLaunchedEffect

private const val TAG = "[김진영] NotificationScreen"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationScreen(
    navController: NavController,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    NotificationLaunchedEffect(navController = navController)
    val notificationList by notificationViewModel.notificationListState.collectAsState()
    LaunchedEffect(Unit) {
        notificationViewModel.getNotificationHistory()
    }

    var isRemoved by remember { mutableStateOf(false) }
    var removedItem by remember { mutableStateOf(-1) }
    LaunchedEffect(removedItem) {
        if (removedItem != -1) {
            notificationViewModel.deleteNotificationHistory(removedItem)
        }
    }

    val scope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = notificationList,
            key = { item -> item.hashCode() }
        ) { notificationItem ->
            NotificationItem(notificationItem, onRemove = { currentItem ->
                Log.d(TAG, "NotificationScreen: $currentItem")
                removedItem = currentItem.id
            })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = rememberNavController())
}