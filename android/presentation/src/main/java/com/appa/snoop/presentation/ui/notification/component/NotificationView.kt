package com.appa.snoop.presentation.ui.notification.component

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appa.snoop.domain.model.notification.Notification
import com.appa.snoop.presentation.R
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay

private const val TAG = "[김진영] NotificationView"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(
    item: Notification,
    onRemove: (Notification) -> Unit,
    onClickedItem: () -> Unit
) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                true
            } else false
        }, positionalThreshold = { 400f }
    )
    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                NotificationItemComponent(item, onClickedItem = {
                    onClickedItem()
                })
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }

    LaunchedEffect(show) {
        Log.d(TAG, "NotificationItem: show")
        if (!show) {
            Log.d(TAG, "NotificationItem: !show")
            onRemove(currentItem)
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.White // dismissThresholds 만족 안한 상태
            DismissValue.DismissedToEnd -> Color.Transparent // -> 방향 스와이프 (수정)
            DismissValue.DismissedToStart -> Color.LightGray.copy(alpha = 0.5f) // <- 방향 스와이프 (삭제)
        }, label = "",
        animationSpec = tween(durationMillis = 250)
    )

    val iconColor by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray.copy(alpha = 0.5f)
            DismissValue.DismissedToEnd -> Color.Transparent // -> 방향 스와이프 (수정)
            DismissValue.DismissedToStart -> Color.White // <- 방향 스와이프 (삭제)
        }, label = "",
        animationSpec = tween(durationMillis = 250)
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            Icons.Default.Delete,
            contentDescription = "Delete",
            tint = iconColor
        )
    }
}


@Composable
fun NotificationSettingComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "다양한 혜택 알림을 받으려면 알림을 켜주세요.", fontWeight = FontWeight.Normal, fontSize = 12.ssp)
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_forward),
            contentDescription = "알림 설정 화면 이동",
            colorFilter = ColorFilter.tint(Color.Gray)
        )
    }
}
