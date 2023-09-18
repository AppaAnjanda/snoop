package com.appa.snoop.presentation.ui.login.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.InvalidRedColor
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPasswordTextField(
    modifier: Modifier = Modifier,
    title: String = "입력",
    text: String = "",
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(title) },
        shape = RoundedCornerShape(10.sdp),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    modifier = Modifier
                        .padding(8.sdp),
                    imageVector = if (passwordVisibility) ImageVector.vectorResource(id = R.drawable.ic_visibility) else ImageVector.vectorResource(id = R.drawable.ic_visibility_off),
                    contentDescription = "password visibility",
                    tint = DarkGrayColor
                )
            }
        },
        // valid 체크로 색 분기 태우기
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = DarkGrayColor,
            unfocusedBorderColor = DarkGrayColor,
            cursorColor = DarkGrayColor,
            focusedLabelColor = DarkGrayColor,
            unfocusedLabelColor = DarkGrayColor,
        ),
        modifier = modifier,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.moveFocus(FocusDirection.Next)
        })
    )
}