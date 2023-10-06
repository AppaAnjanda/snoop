package com.appa.snoop.presentation.ui.category.utils

fun convertNaverUrl(originalUrl: String): String {
    val baseUrl = "https://search.shopping.naver.com/catalog/"
    val idStartIndex = originalUrl.indexOf("id=")
    if (idStartIndex != -1) {
        val idValue = originalUrl.substring(idStartIndex + 3)
        return baseUrl + idValue
    }
    return originalUrl
}
