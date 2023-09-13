package com.appa.snoop.presentation.product.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AlarmSnackBar(
    hostState: SnackbarHostState,
    price: String,
    percent: Int
) {
    SnackbarHost(
        modifier = Modifier.fillMaxWidth(),
        hostState = hostState
    ) {
        CustomSnackBar(
            drawableRes = R.drawable.ic_filled_alarm_product,
            message = "가격 ${price}원에 알림 드릴게요!",
            percent = percent
        )
    }
}

@Composable
fun CustomSnackBar(
    @DrawableRes drawableRes: Int,
    message: String,
    percent: Int
) {
    Snackbar {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = drawableRes),
                    contentDescription = "알림"
                )
                Spacer(modifier = Modifier.width(4.sdp))
                Text(message)
            }
            Text(text = "(현재가 -$percent%)")
        }
    }
}

@Preview
@Composable
fun PreviewAlarmSnackBar() {
    AlarmSnackBar(
        SnackbarHostState(),
        "2,640,000",
        10)
}