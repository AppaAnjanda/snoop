package com.appa.snoop.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.usecase.register.GetLoginStatusUseCase
import com.appa.snoop.domain.usecase.register.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    var loginState by mutableStateOf(false)
        private set

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
}