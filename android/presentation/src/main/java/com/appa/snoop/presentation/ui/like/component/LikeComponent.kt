package com.appa.snoop.presentation.ui.like.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun SelelctComponent(
    checkState: Boolean,
    onChangeCheckedState: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.sdp, end = 16.sdp, top = 16.sdp, bottom = 4.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AllDelete("모두 선택", checkState) { onChangeCheckedState() }
            Text(
                text = "|",
                style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
            )
            Spacer(modifier = Modifier.width(4.sdp))
            Text(
                text = "삭제",
                style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
            )
        }
    }
}

@Composable
fun AllDelete(text: String, value: Boolean, onCheckedChange: (Any) -> Unit) {
    Checkbox(
        checked = value,
        onCheckedChange = onCheckedChange,
        modifier = Modifier.size(16.sdp),
        colors = CheckboxDefaults.colors(
            uncheckedColor = Color.Gray
        )
    )
    Text(
        text = text,
        style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(4.sdp)
            .noRippleClickable {
                onCheckedChange(true)
            }
    )

}
