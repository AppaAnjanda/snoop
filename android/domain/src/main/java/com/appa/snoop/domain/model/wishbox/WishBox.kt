package com.appa.snoop.domain.model.wishbox

data class WishBox(
    val alertPrice: Int,
    val alertYn: Boolean,
    val price: Int,
    val productCode: String,
    val productImage: String,
    val productName: String,
    val wishboxId: Int
)
