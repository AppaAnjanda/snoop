package com.appa.snoop.presentation.util

import java.text.NumberFormat
import java.util.Locale

object PriceUtil {
    fun formatPrice(rawInput: String): String {
        val parsedNumber = rawInput.replace(",", "").toLongOrNull() ?: 0L
        return NumberFormat.getNumberInstance(Locale.US).format(parsedNumber)
    }

    fun parseFormattedPrice(formattedPrice: String): Int {
        val parsedNumber = formattedPrice.replace(",", "").toLongOrNull() ?: 0L
        return parsedNumber.toInt()
    }

    fun calculateDiscountPercentage(originalPrice: Int, discountedPrice: Int): Double {
        if (originalPrice <= 0) return 0.0 // 원래 가격이 0이하라면 할인율은 0%

        val discountAmount = originalPrice - discountedPrice
        val percentage = (discountAmount / originalPrice.toDouble()) * 100
        return kotlin.math.round(percentage * 100) / 100.0
    }
}