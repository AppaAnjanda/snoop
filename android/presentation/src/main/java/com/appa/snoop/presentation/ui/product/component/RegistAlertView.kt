package com.appa.snoop.presentation.ui.product.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.ui.mypage.component.CustomAlertDialog
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun RegistAlertDialog(
    visible: Boolean,
    focusManager: FocusManager,
    onValueChanged: (String) -> Unit,
    onWishboxRequest: () -> Unit,
    onAlertRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var text by remember { mutableStateOf("") }
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
                Spacer(modifier = Modifier.size(16.sdp))
                Text(
                    text = "지정가 설정",
                    style = TextStyle(
                        fontSize = 14.ssp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.size(24.sdp))
                AlertTextField(focusManager = focusManager, onValueChanged = {
                    onValueChanged(it)
                    text = it
                })
                Spacer(modifier = Modifier.size(20.sdp))
                RegistAlertButton(
                    text = text,
                    onWishboxRequest = { onWishboxRequest() },
                    onAlertRequest = { onAlertRequest() }
                )
                Spacer(modifier = Modifier.size(12.sdp))
            }
        }
    }
}

@Composable
fun AlertTextField(focusManager: FocusManager, onValueChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var price by remember {
        mutableStateOf("")
    }
//    price = PriceUtil.formatPrice(text)
    TextField(
        value = price,
        onValueChange = {
            price = PriceUtil.formatPrice(it)
            onValueChanged(price)
        },
        textStyle = TextStyle(
            fontSize = 12.ssp,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.moveFocus(FocusDirection.Down)
            onValueChanged(price)
            keyboardController?.hide()
        }),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 28.sdp, end = 28.sdp)
            .clip(RoundedCornerShape(12.sdp))
            .border(0.7.dp, Color.LightGray, RoundedCornerShape(12.sdp)),
        shape = ShapeDefaults.Medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        suffix = {
            Text(
                text = "원",
                fontSize = 12.ssp
            )
        },
    )
}

@Composable
fun RegistAlertButton(
    text: String,
    onWishboxRequest: () -> Unit,
    onAlertRequest: () -> Unit
) {
    Row(
        modifier = Modifier.padding(start = 26.sdp, end = 26.sdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onWishboxRequest() },
            modifier = Modifier
                .weight(1f)
                .height(40.sdp),
            colors = ButtonDefaults.buttonColors(WhiteColor),
            border = BorderStroke(0.5.dp, Color.LightGray)
        ) {
            Text(
                text = "찜 등록",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.ssp
                )

            )
        }
        Spacer(modifier = Modifier.width(8.sdp))
        Button(
            onClick = { onAlertRequest() },
            modifier = Modifier
                .weight(1f)
                .height(40.sdp),
            enabled = text.isNotEmpty()
        ) {
            Text(
                text = "지정가 등록",
                fontSize = 12.ssp
            )
        }
    }
}
