package com.appa.snoop.domain.model.category

data class ProductPaging(
    val contents: List<Product>,
    val currentPage: Int,
    val totalPage: Int
)