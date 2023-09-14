package com.appa.snoop.presentation.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.appa.snoop.presentation.ui.product.component.AlarmSnackBar
import com.appa.snoop.presentation.ui.product.component.ButtonView
import com.appa.snoop.presentation.ui.product.component.BuyTimingView
import com.appa.snoop.presentation.ui.product.component.ProductDetailView
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var alarmChecked by remember { mutableStateOf(false) }

    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    // TODO(Domain model 가져오면 추후에 교체)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = WhiteColor)
            .padding(16.sdp),
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.sdp)
        ) {
            ProductDetailView(
                onSharedClicked = { url ->
                    // TODO(URL로 공유하기)
                }
            )
            BuyTimingView()
        }
        AlarmSnackBar(
            hostState = snackState,
            price = "2,640,000",
            percent = 10
        )
        Spacer(modifier = Modifier.height(16.sdp))
        ButtonView(
            alarmChecked = alarmChecked,
            onBuyClicked = { /*TODO*/ },
            onAlarmClicked = {
                if (snackState.currentSnackbarData == null) {
                    alarmChecked = !alarmChecked
                    // 알람을 클릭이 되었을때 SnackBar Show
                    if (alarmChecked) {
                        coroutineScope.launch {
                            val job = coroutineScope.launch {
                                snackState.showSnackbar("",
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                            delay(1500L)
                            job.cancel()
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewProductDetailScreen() {
    ProductDetailScreen()
}