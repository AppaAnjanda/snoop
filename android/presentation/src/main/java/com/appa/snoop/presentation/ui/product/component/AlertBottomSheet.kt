package com.appa.snoop.presentation.ui.product.component

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.appa.snoop.presentation.ui.category.component.PriceTextField
import com.appa.snoop.presentation.ui.mypage.MyPageViewModel
import com.appa.snoop.presentation.ui.product.ProductViewModel
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertBottomSheet(
    viewModel: ProductViewModel,
    sheetState: SheetState,
    focusManager: FocusManager,
    onValueChanged: (String) -> Unit,
    onDismiss: () -> Unit
) {

//    val cards by viewModel.cardsState.collectAsState()

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = WhiteColor,
        contentColor = WhiteColor,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "지정가 설정",
                style = TextStyle(
                    fontSize = 14.ssp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )
            AlertTextField(focusManager = focusManager, onValueChanged = {
                onValueChanged(it)
            })
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
            .fillMaxWidth().padding(start = 16.sdp, end = 16.sdp)
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
