package com.appa.snoop.presentation.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

object UrlUtil {
    suspend fun encodeProductCode(productCode: String): String {
        val encoder = withContext(Dispatchers.IO) {
            URLEncoder.encode(productCode, "utf-8")
        }
        return encoder
    }
}