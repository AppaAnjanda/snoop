package com.appa.snoop.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.appa.snoop.presentation.R

val NanumSquareNeo = FontFamily(
    Font(R.font.nanum_square_neo_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.nanum_square_neo_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.nanum_square_neo_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.nanum_square_neo_extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
)

val GmarketSans = FontFamily(
    Font(R.font.gmarket_sans_bold, FontWeight.Bold, FontStyle.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NanumSquareNeo
    )
)