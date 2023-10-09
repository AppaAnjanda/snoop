package com.appa.snoop.domain.usecase.wishbox

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.repository.WishBoxRepository
import javax.inject.Inject

class UpdateWishBoxPriceUseCase @Inject constructor(
    private val wishBoxRepository: WishBoxRepository
) {
    suspend operator fun invoke(wishboxId: Int, alertPrice: AlertPrice): NetworkResult<WishBox> {
        return wishBoxRepository.updateWishBoxPrice(wishboxId = wishboxId, alertPrice = alertPrice)
    }
}
