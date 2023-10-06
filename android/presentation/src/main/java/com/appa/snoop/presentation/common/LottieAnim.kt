package com.appa.snoop.presentation.common

import android.annotation.SuppressLint
import android.util.Log
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
fun LottieAnim(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    res: Int,
    startTime: Float = 0f,
    endTime: Float = 1f,
    onClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(res))
//    var checked by remember { mutableStateOf(isChecked) }
    var isPlaying by remember { mutableStateOf(false) }

//    Log.d(TAG, "LottieAnim: 하트가 체크 되어있는지 $isChecked")

    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = false,
        isPlaying = isPlaying,
//        speed = if (checked) 1f else -1f,
        speed = 1f,
        clipSpec = LottieClipSpec.Progress(startTime, endTime)
    )

    LottieAnimation(
        composition,
        progress = {
            isPlaying = isChecked
            if (isChecked) progress else 0.0f
        },
        modifier = modifier
            .noRippleClickable{
                isPlaying = isChecked
//                checked = !checked
//                isChecked = !isChecked
                onClick()
            }
    )
}