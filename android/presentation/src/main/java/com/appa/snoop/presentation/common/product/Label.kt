package com.appa.snoop.presentation.common.product

sealed interface Label {
    val showPrice: Boolean
    val showLiked: Boolean
    val showShared: Boolean
    val showDeleted: Boolean
}

object HomeLabel: Label {
    override val showPrice: Boolean = true
    override val showLiked: Boolean = true
    override val showShared: Boolean = false
    override val showDeleted: Boolean = false
}