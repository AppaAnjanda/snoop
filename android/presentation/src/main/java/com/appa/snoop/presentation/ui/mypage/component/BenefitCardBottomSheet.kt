package com.appa.snoop.presentation.ui.mypage.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.appa.snoop.presentation.ui.mypage.MyPageViewModel
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(viewModel: MyPageViewModel, sheetState: SheetState, onDismiss: () -> Unit) {

    val cards by viewModel.cardsState.collectAsState()


    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = WhiteColor,
        contentColor = WhiteColor,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        LazyColumn(modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)) {
            itemsIndexed(cards) { index, item ->
                var checkState by remember {
                    mutableStateOf(item.checked)
                }
                CheckBoxRow(text = item.name, value = checkState) {
                    checkState = !checkState
                }
                cards[index].checked = checkState
            }
            item {
                Button(
                    onClick = { /* TODO 카드 저장 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.sdp)
                        .padding(4.sdp),
                    shape = RoundedCornerShape(10.sdp),
                    colors = ButtonDefaults.buttonColors(PrimaryColor),
                    elevation = ButtonDefaults.buttonElevation(2.sdp)
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "혜택 카드 저장",
                            fontSize = 14.ssp
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CheckBoxRow(text: String, value: Boolean, onClick: (Any) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = value, onCheckedChange = onClick)
        ClickableText(
            text = AnnotatedString(text), onClick = onClick, modifier = Modifier.fillMaxWidth()
        )
    }
}

data class BenefitCard(
    val name: String,
    var checked: Boolean
)
