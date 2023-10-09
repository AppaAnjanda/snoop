package com.appa.snoop.domain.usecase.wishbox

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.wishbox.WishBoxDeleteList
import com.appa.snoop.domain.repository.WishBoxRepository
import javax.inject.Inject

class DeleteWishBoxListUseCase @Inject constructor(
    private val wishBoxRepository: WishBoxRepository
) {
    suspend operator fun invoke(wishBoxDeleteList: WishBoxDeleteList): NetworkResult<List<Int>> {
        return wishBoxRepository.deleteListWishBox(wishBoxDeleteList = wishBoxDeleteList)
    }
}
