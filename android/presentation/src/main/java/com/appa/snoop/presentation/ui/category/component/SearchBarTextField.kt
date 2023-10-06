package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.RedColor
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarTextField(
    modifier: Modifier = Modifier,
    title: String = "입력",
    text: String = "",
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions,
    onIconClick: () -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(title) },
        shape = RoundedCornerShape(10.sdp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = DarkGrayColor,
            unfocusedBorderColor = DarkGrayColor,
            cursorColor = DarkGrayColor,
            focusedLabelColor = DarkGrayColor,
            unfocusedLabelColor = DarkGrayColor,
        ),
        modifier = modifier,
        keyboardActions = keyboardActions,
        enabled = enabled,
        trailingIcon = {
            Surface(
                shape = RoundedCornerShape(20.sdp),
                onClick = {
                    onIconClick()
                },
                modifier = Modifier
                    .padding(2.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "검색버튼",
                )
            }
        }
    )
}