package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
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
    focusManager: FocusManager,
    enabled: Boolean = true,
    isCerted: Boolean = false,
    keyboardActions: KeyboardActions
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
            disabledBorderColor = if (isCerted) BlueColor else RedColor,
            disabledLabelColor = if (isCerted) BlueColor else DarkGrayColor
        ),
        modifier = modifier,
        keyboardActions = keyboardActions,
        enabled = enabled
    )
}