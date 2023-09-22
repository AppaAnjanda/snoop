package com.appa.snoop.presentation.ui.category

import com.appa.snoop.domain.model.category.CategoryItem

object CategoryList {
    val list: List<CategoryItem>
        get() = listOf(
            CategoryItem("디지털가전", this.digitalMinorList),
            CategoryItem("가구", this.furnitureMinorList),
            CategoryItem("생활용품", this.necessariesMinorList),
            CategoryItem("식품", this.foodMinorList)
        )

    private val digitalMinorList = listOf(
        "TV", "냉장고", "세탁기", "청소기", "노트북", "데스크탑", "키보드", "마우스", "모니터"
    )
    private val furnitureMinorList = listOf(
        "침대", "쇼파", "책상", "옷장"
    )
    private val necessariesMinorList = listOf(
        "주방", "욕실", "청소", "수납"
    )
    private val foodMinorList = listOf(
        "음료", "과일", "채소", "과자", "축산", "가공식품"
    )
}