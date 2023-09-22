package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.domain.model.category.Product

fun ProductResponse.toDto(): Product {
    return Product (
        id = id,
        code = code,
        majorCategory = majorCategory,
        minorCategory = minorCategory,
        provider = provider,
        price = price,
        productName = productName,
        productLink = productLink,
        productImage = productImage,
        timestamp = timestamp
    )
}