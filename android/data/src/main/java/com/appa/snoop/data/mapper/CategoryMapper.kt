package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.category.response.ProductPagingResponse
import com.appa.snoop.data.model.category.response.ProductResponse
import com.appa.snoop.data.model.category.response.WishToggleResponse
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.model.category.Wish

fun ProductResponse.toDomain(): Product {
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
        timestamp = timestamp,
        wishYn = wishYn
    )
}

fun ProductPagingResponse.toDto(): ProductPaging {
    return ProductPaging(
        contents = contents.map{ it.toDomain() },
        currentPage = currentPage,
        totalPage = totalPage
    )
}

fun WishToggleResponse.toDomain(): Wish {
    return Wish(
        wishYn = wishYn
    )
}