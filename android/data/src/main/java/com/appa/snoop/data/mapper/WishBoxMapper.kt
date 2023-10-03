package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.wishbox.request.WishBoxDeleteListRequest
import com.appa.snoop.data.model.wishbox.response.WishBoxDeleteResponse
import com.appa.snoop.data.model.wishbox.response.WishBoxResponse
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.model.wishbox.WishBoxDelete
import com.appa.snoop.domain.model.wishbox.WishBoxDeleteList

fun WishBoxResponse.toDomain(): WishBox {
    return WishBox(
        alertPrice = alertPrice,
        alertYn = alertYn,
        price = price,
        productCode = productCode,
        productImage = productImage,
        productName = productName,
        wishboxId = wishboxId
    )
}

fun WishBoxDeleteResponse.toDomain(): WishBoxDelete {
    return WishBoxDelete(
        removeId = removeId
    )
}

fun WishBoxDeleteList.toDto(): WishBoxDeleteListRequest {
    return WishBoxDeleteListRequest(
        wishboxIds = wishboxIds
    )
}