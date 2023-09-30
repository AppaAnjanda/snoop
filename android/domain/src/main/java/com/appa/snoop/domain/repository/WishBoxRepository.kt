package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.product.WishProduct
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.model.wishbox.WishBoxDelete

interface WishBoxRepository {
    suspend fun getWishBoxList(
    ): NetworkResult<List<WishBox>>

    suspend fun deleteWishBox(
        wishboxId: Int
    ): NetworkResult<WishBoxDelete>

    suspend fun updateWishBoxPrice(
        wishboxId: Int,
        alertPrice: AlertPrice
    ): NetworkResult<WishBox>
}