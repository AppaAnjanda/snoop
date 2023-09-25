package com.appa.snoop.domain.model.category

data class Product (
    val id: String,
    val code: String,
    val majorCategory: String,
    val minorCategory: String,
    val provider: String,
    val price: Int,
    val productName: String,
    val productLink: String,
    val productImage: String,
    val timestamp: String,
    val wishYn: Boolean
)