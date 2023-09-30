package com.appa.snoop.presentation.ui.chatting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PlatformImeOptions
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BottomChatFieldView(
    modifier: Modifier = Modifier,
    chatText: String,
    focusManager: FocusManager,
    onTextChanged: (String) -> Unit,
    onSendClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(top = 8.sdp, bottom = 8.sdp, start = 16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = modifier
                .weight(1f)
                .height(32.sdp)
                .clip(RoundedCornerShape(size = 10.sdp))
                .background(color = BackgroundColor)
                .padding(horizontal = 16.sdp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(max = 200.sdp),
                value = chatText,
                onValueChange = onTextChanged,
                textStyle = TextStyle(
                    fontSize = 12.ssp,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onSendClicked()
                    }
                )
            )
        }
        IconButton(
            modifier = modifier.size(40.sdp),
            onClick = onSendClicked,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = PrimaryColor,
                disabledContentColor = DarkGrayColor
            ),
            enabled = chatText.isNotBlank(),
        ) {
            Icon(
                modifier = modifier.size(24.sdp),
                painter = painterResource(R.drawable.ic_send),
                contentDescription = "보내기 아이콘",
            )
        }
    }
}

//@Preview
//@Composable
//fun PreviewBottomChatFieldView() {
//    BottomChatFieldView(
//        chatText = "테스트",
//        onSendClicked = {},
//        onTextChanged = {}
//    )
//}