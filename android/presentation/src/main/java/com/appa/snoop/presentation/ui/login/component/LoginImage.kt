package com.appa.snoop.presentation.ui.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.LottieAnim
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LoginImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.sdp)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_meerkat))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1000
        )
        LottieAnimation(
            composition = composition,
            progress = progress
        )
    }
}