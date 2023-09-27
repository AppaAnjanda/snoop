package com.appa.snoop.domain.model.product

data class Timing(
    val avgPrice: Int,
    val curPrice: Int,
    val diffPercent: Double,
    val timing: String
)
