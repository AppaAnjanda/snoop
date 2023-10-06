package com.appa.snoop.domain.usecase.wishbox

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.wishbox.WishBoxDelete
import com.appa.snoop.domain.repository.WishBoxRepository
import javax.inject.Inject

class DeleteWishBoxUseCase @Inject constructor(
    private val wishBoxRepository: WishBoxRepository
) {
    suspend operator fun invoke(wishboxId: Int): NetworkResult<WishBoxDelete> {
        return wishBoxRepository.deleteWishBox(wishboxId = wishboxId)
    }
}
