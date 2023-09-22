package com.appa.snoop.presentation.util

import java.text.NumberFormat
import java.util.Locale

object PriceUtil {
    fun formatPrice(rawInput: String): String {
        val parsedNumber = rawInput.replace(",", "").toLongOrNull() ?: 0L
        return NumberFormat.getNumberInstance(Locale.US).format(parsedNumber)
    }
}