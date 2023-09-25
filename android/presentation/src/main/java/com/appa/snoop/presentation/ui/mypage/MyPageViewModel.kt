package com.appa.snoop.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.usecase.member.GetMemberInfoUseCase
import com.appa.snoop.presentation.ui.mypage.component.BenefitCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김진영] MyPageViewModel"

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMemberInfoUsecase: GetMemberInfoUseCase
) : ViewModel() {
    private val _cardsState = MutableStateFlow(emptyList<BenefitCard>())
    val cardsState: StateFlow<List<BenefitCard>> = _cardsState.asStateFlow()

    init {
        _cardsState.value = listOf(
            BenefitCard("신한카드", true),
            BenefitCard("하나카드", false),
            BenefitCard("우리카드", false),
            BenefitCard("삼성카드", false),
        )
    }

    private val _memberInfo = MutableStateFlow<Member>(Member("", "", listOf(), ""))
    val memberInfo = _memberInfo.asStateFlow()

    fun getMemberInfo() {
        viewModelScope.launch {
            val result = getMemberInfoUsecase.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    _memberInfo.emit(result.data)
                    Log.d(TAG, "getMemberInfo: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getMemberInfo: 멤버 정보 불러오기 실패")
                }
            }
        }
    }
}