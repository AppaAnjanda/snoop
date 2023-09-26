package com.appa.snoop.presentation.ui.product.data

enum class PriceLevel(val timing: String, val index: Int) {
    VERY_CHEAP("완전 싸다", 0),
    CHEAP("싸다", 1),
    SOMEWHAT_CHEAP("약간 싸다", 2),
    NORMAL("보통", 3),
    SOMEWHAT_EXPENSIVE("약간 비싸다", 4),
    EXPENSIVE("비싸다", 5),
    VERY_EXPENSIVE("완전 비싸다", 6)
}