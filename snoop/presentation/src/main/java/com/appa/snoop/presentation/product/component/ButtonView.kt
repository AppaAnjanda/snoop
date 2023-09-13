package com.appa.snoop.presentation.product.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    alarmChecked: Boolean,
    onBuyClicked: () -> Unit,
    onAlarmClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Button(
            modifier = modifier
                .weight(1f)
                .height(44.sdp),
            onClick = { onBuyClicked() },
            colors = ButtonDefaults.buttonColors(
                contentColor = WhiteColor,
                containerColor = PrimaryColor
            ),
            shape = RoundedCornerShape(10.sdp)
        ) {
            Text(
                text = "사러갈래요!",
                fontSize = 16.ssp,
            )
        }
        Spacer(modifier = modifier.width(16.sdp))
        Box(
            modifier = modifier
                .size(44.sdp)
                .clip(RoundedCornerShape(10.sdp))
                .border(
                    border = BorderStroke(1.dp, color = DarkGrayColor),
                    shape = RoundedCornerShape(10.sdp)
                )
                .background(color = WhiteColor)
                .noRippleClickable {
                    onAlarmClicked()
                }
        ) {
            Image(
                painter = painterResource(
                    id = if (alarmChecked) R.drawable.ic_filled_alarm_product else R.drawable.ic_alarm_product),
                contentDescription = "알림",
                modifier = modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview
@Composable
fun PreviewButtonView() {
    ButtonView(
        alarmChecked = false,
        onBuyClicked = {},
        onAlarmClicked = {}
    )
}