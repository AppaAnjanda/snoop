package com.appa.snoop.presentation.ui.mypage.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(sheetState: SheetState, onDismiss: () -> Unit) {

    var cardList = listOf(Card("삼성카드", false), Card("우리카드", false), Card("하나카드", false))
    var cards = mutableStateListOf(Card("삼성카드", false), Card("우리카드", false), Card("하나카드", false))


    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        LazyColumn() {
            itemsIndexed(cardList) { index, item ->
                var checkState by remember {
                    mutableStateOf(item.checked)
                }
                CheckBoxRow(text = item.name, value = checkState) {
                    checkState = !checkState
                }
            }
        }
    }
}

@Composable
fun CheckBoxRow(text: String, value: Boolean, onClick: (Any) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = value, onCheckedChange = onClick)
        ClickableText(
            text = AnnotatedString(text), onClick = onClick, modifier = Modifier.fillMaxWidth()
        )
    }
}

data class Card(
    val name: String,
    var checked: Boolean
)
