package com.appa.snoop.presentation.ui.product.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.BlueColor
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.RedColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun BuyTimingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.sdp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "지금!",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrayColor,
                )
            )
            Text(
                text = "고민좀..",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrayColor,
                )
            )
            Text(
                text = "지켜봐",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrayColor,
                )
            )
        }
        Box {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(4.sdp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                BlueColor,
                                BlueColor.copy(0.5f),
                                BlueColor.copy(0.5f),
                                DarkGrayColor.copy(0.5f),
                                DarkGrayColor.copy(0.5f),
                                DarkGrayColor.copy(0.5f),
                                RedColor.copy(0.5f),
                                RedColor.copy(0.5f),
                                RedColor

                            )
                        )
                    )
            )
            Icon(
                painter = painterResource(id = R.drawable.img_finger),
                contentDescription = "손가락",
                modifier = modifier.size(24.sdp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBuyTimingView() {
    BuyTimingView()
}