package com.appa.snoop.domain.model.product

data class WishProduct(
    val alertPrice: Int,
    val alertYn: Boolean,
    val productCode: String,
    val provider: String,
    val wishboxId: Int
)