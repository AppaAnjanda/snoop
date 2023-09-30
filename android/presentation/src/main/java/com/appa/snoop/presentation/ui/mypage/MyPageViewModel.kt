package com.appa.snoop.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.member.Nickname
import com.appa.snoop.domain.usecase.member.GetMemberInfoUseCase
import com.appa.snoop.domain.usecase.member.GetMyCardListUseCase
import com.appa.snoop.domain.usecase.member.GetRecentProductUseCase
import com.appa.snoop.presentation.ui.mypage.component.BenefitCard
import com.appa.snoop.presentation.util.UrlUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김진영] MyPageViewModel"

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyCardListUseCase: GetMyCardListUseCase,
    private val getRecentProductUseCase: GetRecentProductUseCase
) : ViewModel() {
    private val _cardsState = MutableStateFlow(emptyList<BenefitCard>())
    val cardsState: StateFlow<List<BenefitCard>> = _cardsState.asStateFlow()

    private val _recentProductState =
        MutableStateFlow(listOf(Product("", "", "", "", "", 0, "", "", "", "", false, false)))
    var recentProductState: StateFlow<List<Product>> = _recentProductState.asStateFlow()

    init {
        _cardsState.value = listOf(
            BenefitCard("신한카드", true),
            BenefitCard("하나카드", false),
            BenefitCard("우리카드", false),
            BenefitCard("삼성카드", false),
        )
    }

//    fun getMyCardList() {
//        viewModelScope.launch {
//            val result = getMyCardListUseCase.invoke()
//
//            when (result) {
//                is NetworkResult.Success -> {
//                    _cardsState.emit(result.data)
//                    Log.d(TAG, "getMyCardList: ${result.data}")
//                }
//
//                else -> {
//                    Log.d(TAG, "getMyCardList: 카드 조회 실패")
//                }
//            }
//        }
//    }

    fun getRecentProduct() {
        viewModelScope.launch {
            val result = getRecentProductUseCase.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    _recentProductState.emit(result.data)
                    Log.d(TAG, "getRecentProduct: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getRecentProduct: 최근 상품 목록 조회 실패")
                }
            }
        }
    }
    
    

}