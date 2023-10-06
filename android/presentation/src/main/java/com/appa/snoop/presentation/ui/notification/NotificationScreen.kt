package com.appa.snoop.presentation.ui.notification

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.notification.component.NotificationItem
import com.appa.snoop.presentation.ui.notification.viewmodel.NotificationViewModel
import com.appa.snoop.presentation.util.effects.NotificationLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

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

    var removedItem by remember { mutableStateOf(-1) }
    LaunchedEffect(removedItem) {
        if (removedItem != -1) {
            notificationViewModel.deleteNotificationHistory(removedItem)
        }
    }

    if (notificationList.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = notificationList,
                key = { item -> item.hashCode() }
            ) { notificationItem ->
                NotificationItem(notificationItem,
                    onRemove = { currentItem ->
                        Log.d(TAG, "NotificationScreen: $currentItem")
                        removedItem = currentItem.id
                    },
                    onClickedItem = {
                        val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                            "{productCode}",
                            notificationItem.productCode
                        )
                        navController.navigate(route)
                    })
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = rememberNavController())
}