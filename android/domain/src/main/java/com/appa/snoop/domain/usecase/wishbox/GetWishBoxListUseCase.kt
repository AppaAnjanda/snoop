package com.appa.snoop.domain.usecase.wishbox

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.repository.WishBoxRepository
import javax.inject.Inject

class GetWishBoxListUseCase @Inject constructor(
    private val wishBoxRepository: WishBoxRepository
) {
    suspend operator fun invoke(): NetworkResult<List<WishBox>> {
        return wishBoxRepository.getWishBoxList()
    }
}
