package com.appa.snoop.presentation.ui.home.common

import com.appa.snoop.presentation.R

enum class HomeTitle(
    val titleName: String,
    val res: Int
) {
    DIGITAL("디지털/가전", R.drawable.img_notebook),
    NECESSARIES("생활용품", R.drawable.img_necessaries),
    FURNITURE("가구", R.drawable.img_furniture),
    FOOD("식품", R.drawable.img_food)
}