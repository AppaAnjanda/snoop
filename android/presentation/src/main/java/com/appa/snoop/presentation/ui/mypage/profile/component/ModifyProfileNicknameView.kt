package com.appa.snoop.presentation.ui.mypage.profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ModifyProfilNickname(onNicknameChange: (String) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.sdp),
        text = "닉네임",
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.ssp,
            color = Color.DarkGray
        )
    )
    Spacer(modifier = Modifier.size(8.sdp))

    val keyboardController = LocalSoftwareKeyboardController.current
    var nickName by remember { mutableStateOf("기웃기웃") }
    TextField(
        value = nickName,
        onValueChange = { nickName = it },
        textStyle = TextStyle(
            fontSize = 12.ssp,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onSearch = {
            onNicknameChange(nickName)
            keyboardController?.hide()
        }),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.sdp))
            .border(1.sdp, Color.LightGray, RoundedCornerShape(12.sdp)),
        shape = ShapeDefaults.Medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent
        )
    )
}