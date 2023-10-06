package com.appa.snoop.presentation.ui.category.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.appa.snoop.presentation.util.extensions.noRippleClickable

private const val TAG = "[김희웅] LottieAnim"
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun LoadingLottieAnimation(
    modifier: Modifier = Modifier,
    res: Int,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(res))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = Int.MAX_VALUE
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = {
            progress
        },
    )
}