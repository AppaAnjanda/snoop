package com.appa.snoop.presentation.ui.like

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.model.wishbox.WishBoxDelete
import com.appa.snoop.domain.model.wishbox.WishBoxDeleteList
import com.appa.snoop.domain.usecase.wishbox.DeleteWishBoxListUseCase
import com.appa.snoop.domain.usecase.wishbox.DeleteWishBoxUseCase
import com.appa.snoop.domain.usecase.wishbox.GetWishBoxListUseCase
import com.appa.snoop.domain.usecase.wishbox.UpdateWishBoxPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김진영] LikeViewModel"

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val deleteWishBoxUseCase: DeleteWishBoxUseCase,
    private val getWishBoxListUseCase: GetWishBoxListUseCase,
    private val updateWishBoxPriceUseCase: UpdateWishBoxPriceUseCase,
    private val deleteWishBoxListUseCase: DeleteWishBoxListUseCase
) : ViewModel() {
    private val _wishboxListState =
        MutableStateFlow(emptyList<WishBox>())
    var wishboxListState: StateFlow<List<WishBox>> = _wishboxListState.asStateFlow()

    private val _deleteWishboxState =
        MutableStateFlow(WishBoxDelete(0))
    var deleteWishboxState: StateFlow<WishBoxDelete> = _deleteWishboxState.asStateFlow()

    private val _deleteWishboxListState =
        MutableStateFlow(emptyList<Int>())
    val deleteWishboxListState: StateFlow<List<Int>> = _deleteWishboxListState.asStateFlow()

    private val _updateWishboxState =
        MutableStateFlow(WishBox(0, false, 0, "", "", "", 0))
    var updateWishboxState: StateFlow<WishBox> = _updateWishboxState.asStateFlow()

    fun getWishBoxList() {
        viewModelScope.launch {
            val result = getWishBoxListUseCase.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    _wishboxListState.emit(result.data)
                    Log.d(TAG, "getWishBoxList: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getWishBoxList: 찜 목록 조회 실패!")
                }
            }
        }
    }

    fun deleteWishBox(wishboxId: Int) {
        viewModelScope.launch {
            val result = deleteWishBoxUseCase.invoke(wishboxId = wishboxId)

            when (result) {
                is NetworkResult.Success -> {
                    _deleteWishboxState.emit(result.data)
                    val updatedList = _wishboxListState.value.filter { it.wishboxId != wishboxId }
                    _wishboxListState.emit(updatedList)
                    Log.d(TAG, "deleteWishBox: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "deleteWishBox: 찜 삭제 실패!")
                }
            }
        }
    }

    fun deleteWishBoxList(wishBoxDeleteList: WishBoxDeleteList) {
        viewModelScope.launch {
            val result = deleteWishBoxListUseCase.invoke(wishBoxDeleteList = wishBoxDeleteList)

            when (result) {
                is NetworkResult.Success -> {
                    _deleteWishboxListState.emit(result.data)
                    val updatedList = _wishboxListState.value.filterNot { item ->
                        wishBoxDeleteList.wishboxIds.any { it == item.wishboxId }
                    }
                    _wishboxListState.emit(updatedList)
                    Log.d(TAG, "deleteWishBoxList: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "deleteWishBoxList: 찜 목록 삭제 실패!")
                }
            }
        }
    }

    fun updateWishBoxPrice(wishboxId: Int, price: Int) {
        viewModelScope.launch {
            val result = updateWishBoxPriceUseCase.invoke(
                wishboxId = wishboxId,
                alertPrice = AlertPrice(price)
            )

            when (result) {
                is NetworkResult.Success -> {
                    _updateWishboxState.emit(result.data)
                    Log.d(TAG, "updateWishBoPrice: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "updateWishBoPrice: 찜 가격 수정 실패!")
                }
            }
        }
    }

    suspend fun refreshLikeList(wishboxId: Int) {
        val updatedList = _wishboxListState.value.filter { it.wishboxId != wishboxId }
        _wishboxListState.emit(updatedList)
    }
}