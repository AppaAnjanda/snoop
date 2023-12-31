package com.appa.snoop.presentation.ui.like.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PriceTextField(
    modifier: Modifier,
    text: String,
    focusManager: FocusManager,
    onPriceChanged: (String) -> Unit,
    onUpdateClick: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    var price by remember {
        mutableStateOf(text)
    }
    var changedState by remember { mutableStateOf(false) }

    price = PriceUtil.formatPrice(text)
    BasicTextField(
        value = price,
        onValueChange = {
            changedState = true
            price = PriceUtil.formatPrice(it)
            onPriceChanged(price)
        },
        modifier = modifier
            .padding(end = 4.sdp)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        textStyle = LocalTextStyle.current.copy(fontSize = 12.ssp, textAlign = TextAlign.Right),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus(true)
            onPriceChanged(price)
            keyboardController?.hide()
        })
    ) { innerTextField ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0xFFEFEFEF),
                    start = Offset(
                        x = size.width / 5,
                        y = size.height - 1.dp.toPx(),
                    ),
                    end = Offset(
                        x = size.width,
                        y = size.height - 1.dp.toPx(),
                    ),
                    strokeWidth = 1.dp.toPx(),
                )
            },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.sdp),
            ) {

                Icon(
                    modifier = Modifier
                        .size(16.sdp)
                        .noRippleClickable {
                            if (changedState) {
                                onUpdateClick(price)
                                changedState = false
                            }
                        },
                    imageVector = if (!changedState) Icons.Rounded.Edit else Icons.Rounded.Done,
                    contentDescription = null,
                    tint = Color(0xFFA8A8A8),
                )
                innerTextField()
                Text(text = "원")
            }
            Spacer(modifier = Modifier.height(4.sdp))
        }
    }

}
