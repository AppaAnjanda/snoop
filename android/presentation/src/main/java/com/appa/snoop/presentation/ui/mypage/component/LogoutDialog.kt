package com.appa.snoop.presentation.ui.mypage.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LogoutDialog(
    visible: Boolean,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (visible) {
        CustomAlertDialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(24.sdp))
                    .background(color = WhiteColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 32.sdp, bottom = 8.sdp),
                    text = "로그아웃 하시겠습니까?",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.ssp
                    )
                )
                Row(
                    modifier = Modifier.padding(12.sdp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .weight(1f)
                            .height(40.sdp),
                        colors = ButtonDefaults.buttonColors(WhiteColor),
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Text(
                            text = "취소",
                            style = TextStyle(
                                color = Color.Black
                            )

                        )
                    }
                    Spacer(modifier = Modifier.width(8.sdp))
                    Button(
                        onClick = { onConfirmRequest() },
                        modifier = Modifier
                            .weight(1f)
                            .height(40.sdp)
                    ) {
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onDismissRequest()
                                },
                            text = "확인"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        content()
    }
}