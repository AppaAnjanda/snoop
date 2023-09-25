package com.appa.snoop.presentation.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.model.member.Member
import com.appa.snoop.domain.usecase.member.GetMemberInfoUseCase
import com.appa.snoop.domain.usecase.register.GetLoginStatusUseCase
import com.appa.snoop.domain.usecase.register.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김진영] MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getMemberInfoUsecase: GetMemberInfoUseCase,
) : ViewModel() {
    var loginState by mutableStateOf(false)
        private set


    private val _memberInfo = MutableStateFlow<Member>(Member("", "", ""))
    val memberInfo = _memberInfo.asStateFlow()

    fun getLoginStatus() {
        viewModelScope.launch {
            val tokens = getLoginStatusUseCase.invoke()

            when (tokens.accessToken) {
                "no_token_error" -> {
                    loginState = false
                }

                else -> {
                    loginState = true
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
            getLoginStatus()
        }
    }

    fun getMemberInfo() {
        viewModelScope.launch {
            val result = getMemberInfoUsecase.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    _memberInfo.emit(result.data)
                    Log.d(TAG,"getMemberInfo: ${result.data}"
                    )
                }

                else -> {
                    Log.d(TAG, "getMemberInfo: 멤버 정보 불러오기 실패")
                }
            }
        }
    }
}