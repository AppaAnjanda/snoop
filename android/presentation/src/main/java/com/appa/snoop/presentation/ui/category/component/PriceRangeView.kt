package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PriceRangeView(
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    categoryViewModel: CategoryViewModel,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PriceTextField(
            modifier = Modifier
                .weight(1f),
            title = "최소금액",
            text = "",
            onValueChange = {

            },
            focusManager = focusManager,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.moveFocus(FocusDirection.Next)
            })
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.sdp),
            text = "~",
            fontSize = 16.ssp
        )
//        Spacer(modifier = Modifier.width(10.sdp))
        PriceTextField(
            modifier = Modifier
                .weight(1f),
            title = "최대금액",
            text = "",
            onValueChange = {

            },
            focusManager = focusManager,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.moveFocus(FocusDirection.Next)
            })
        )
    }
}