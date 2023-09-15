package com.appa.snoop.presentation.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SignupTextField() {
    var text by remember { mutableStateOf("") }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(color= BackgroundColor, width = 1.sdp, shape = RoundedCornerShape(10.sdp))
            .padding(8.sdp),
        value = text,
        onValueChange = { text = it },
        maxLines = 1,
        decorationBox = {}
    )
}

@Composable
fun DuckieTextField(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                text.forEachIndexed { index, char ->
                    DuckieTextFieldCharContainer(
                        text = char,
                        isFocused = index == text.lastIndex,
                    )
                }
                repeat(5 - text.length) {
                    DuckieTextFieldCharContainer(
                        text = ' ',
                        isFocused = false,
                    )
                }
            }
        },
    )
}

@Composable
private fun DuckieTextFieldCharContainer(
    modifier: Modifier = Modifier,
    text: Char,
    isFocused: Boolean,
) {
    val shape = remember { RoundedCornerShape(4.dp) }

    Box(
        modifier = modifier
            .size(
                width = 29.dp,
                height = 40.dp,
            )
            .background(
                color = Color(0xFFF6F6F6),
                shape = shape,
            )
            .run {
                if (isFocused) {
                    border(
                        width = 1.dp,
                        color = Color(0xFFFF8300),
                        shape = shape,
                    )
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text.toString())
    }
}

