package com.appa.snoop.presentation.common

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import com.appa.snoop.presentation.util.extentions.noRippleClickable

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun LottieAnim(
    modifier: Modifier = Modifier,
    isChecked: Boolean = true,
    res: Int,
    startTime: Float = 0f,
    endTime: Float = 1f,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(res))
    var checked by remember { mutableStateOf(isChecked) }
    var isPlaying by remember { mutableStateOf(false) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = false,
        isPlaying = isPlaying,
        speed = if (checked) 1f else -1f,
        clipSpec = LottieClipSpec.Progress(startTime, endTime)
    )

    LottieAnimation(
        composition,
        progress = {
            progress
        },
        modifier = modifier
            .noRippleClickable{
                isPlaying = true
                checked = !checked
            }
    )
}