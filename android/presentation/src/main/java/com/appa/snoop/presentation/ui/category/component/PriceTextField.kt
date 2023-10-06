package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.KeyboardType
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.RedColor
import com.appa.snoop.presentation.util.PriceUtil
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceTextField (
    modifier: Modifier = Modifier,
    title: String = "입력",
    text: String = "",
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions
) {
    OutlinedTextField(
        value = PriceUtil.formatPrice(text),
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(title) },
        shape = RoundedCornerShape(16.sdp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = if (title == "최소금액") BlueColor else RedColor,
            focusedBorderColor = PrimaryColor,
            unfocusedBorderColor = DarkGrayColor,
            cursorColor = DarkGrayColor,
//            focusedLabelColor = if (title == "최소금액") BlueColor else RedColor,
            focusedLabelColor = PrimaryColor,
            unfocusedLabelColor = DarkGrayColor,
        ),
        modifier = modifier,
        keyboardActions = keyboardActions,
        enabled = enabled,
        suffix = {
            Text(
                text = "원",
                fontSize = 10.ssp
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}