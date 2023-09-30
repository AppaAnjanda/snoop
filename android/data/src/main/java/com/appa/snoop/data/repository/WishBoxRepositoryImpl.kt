package com.appa.snoop.data.repository

import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.mapper.toDto
import com.appa.snoop.data.service.WishBoxService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.model.wishbox.WishBoxDelete
import com.appa.snoop.domain.repository.WishBoxRepository
import javax.inject.Inject

class WishBoxRepositoryImpl @Inject constructor(
    private val wishBoxService: WishBoxService
) : WishBoxRepository {
    override suspend fun getWishBoxList(): NetworkResult<List<WishBox>> {
        return handleApi {
            wishBoxService.getWishBoxList().map {
                it.toDomain()
            }
        }
    }

    override suspend fun deleteWishBox(wishboxId: Int): NetworkResult<WishBoxDelete> {
        return handleApi {
            wishBoxService.deleteWishBox(wishboxId).toDomain()
        }
    }

    override suspend fun updateWishBoxPrice(
        wishboxId: Int,
        alertPrice: AlertPrice
    ): NetworkResult<WishBox> {
        return handleApi {
            wishBoxService.updateWishBoxPrice(
                wishboxId = wishboxId,
                alertPrice = alertPrice.toDto()
            )
                .toDomain()
        }
    }

}