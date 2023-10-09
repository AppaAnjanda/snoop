package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.product.request.AlertPriceRequest
import com.appa.snoop.data.model.product.response.GraphItemResponse
import com.appa.snoop.data.model.product.response.TimingResponse
import com.appa.snoop.data.model.product.response.WishProductResponse
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.product.GraphItem
import com.appa.snoop.domain.model.product.Timing
import com.appa.snoop.domain.model.product.WishProduct

fun TimingResponse.toDomain(): Timing {
    return Timing(
        avgPrice = avgPrice,
        curPrice = curPrice,
        diffPercent = diffPercent,
        timing = timing
    )
}
fun GraphItemResponse.toDomain(): GraphItem {
    return GraphItem(
        price = price,
        timestamp = timestamp
    )
}

fun AlertPrice.toDto(): AlertPriceRequest {
    return AlertPriceRequest(
        alertPrice = alertPrice
    )
}

fun WishProductResponse.toDomain(): WishProduct {
    return WishProduct(
        alertPrice = alertPrice,
        alertYn = alertYn,
        productCode = productCode,
        provider = provider,
        wishboxId = wishboxId
    )
}