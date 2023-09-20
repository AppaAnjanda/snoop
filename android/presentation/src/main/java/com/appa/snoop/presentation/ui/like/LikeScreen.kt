package com.appa.snoop.presentation.ui.like

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LikeScreen(
    navController: NavController
) {
    MainLaunchedEffect(navController)

    var checkState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AllDelete("모두 선택", checkState) { checkState = !checkState }
                Spacer(modifier = Modifier.width(4.sdp))
                Text(text = "|")
                Spacer(modifier = Modifier.width(4.sdp))
                Text(text = "삭제")
            }
            Text(text = "편집")
        }
    }
}

@Composable
fun AllDelete(text: String, value: Boolean, onClick: (Any) -> Unit) {

    Checkbox(checked = value, onCheckedChange = onClick)
    Text(
        text = text,
        modifier = Modifier
            .offset(x = (-8).sdp)
            .noRippleClickable {
                onClick(true)
            }
    )


}

@Preview
@Composable
fun LikeScreenPreview() {
    LikeScreen(navController = rememberNavController())
}