package com.appa.snoop.presentation.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.usecase.register.EmailInputUseCase
import com.appa.snoop.domain.usecase.register.GetFCMTokenUseCase
import com.appa.snoop.domain.usecase.register.JwtTokenInputUseCase
import com.appa.snoop.domain.usecase.register.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김희웅] LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val jwtTokenInputUseCase: JwtTokenInputUseCase,
    private val getFCMTokenUseCase: GetFCMTokenUseCase,
    private val emailInputUseCase: EmailInputUseCase
) : ViewModel() {
    var textIdState by mutableStateOf("")
        private set
    var textPasswordState by mutableStateOf("")
        private set

    var idFilledState by mutableStateOf(false)
        private set

    var passwordFilledState by mutableStateOf(false)
        private set

    var isLoginSuccessState by mutableStateOf<Boolean?>(null)
        private set

    var loginButtonClickToggle by mutableStateOf(0)

    // 아이디 입력에대한 처리
    fun setTextId(str: String) {
        textIdState = str
        when {
            textIdState.isNotEmpty() -> idFilledState = true
            textIdState.isEmpty() -> idFilledState = false
        }
    }

    // 비밀번호 입력에 대한 처리
    fun setTextPassword(str: String) {
        textPasswordState = str
        when {
            textPasswordState.isNotEmpty() -> passwordFilledState = true
            textPasswordState.isEmpty() -> passwordFilledState = false
        }
    }


    //    private val _fcm =
//        MutableStateFlow("")
//    var productState: StateFlow<Product> = _productState.asStateFlow()
    private var fcmToken by mutableStateOf("")
        private set


    fun getFcmToken() = viewModelScope.launch {
        fcmToken = getFCMTokenUseCase.invoke()
        Log.d(TAG, "getFcmToken: $fcmToken")
    }

    // 로그인
    fun login() {
        viewModelScope.launch {
            fcmToken = getFCMTokenUseCase.invoke()

            val loginInfo =
                LoginInfo(email = textIdState, fcmToken = fcmToken, password = textPasswordState)
            val result = loginUseCase.invoke(loginInfo)

            when (result) {
                is NetworkResult.Success -> {
                    // token 값 sharedpreference에 넣어주자
                    Log.d(TAG, "login에 성공하였습니다. ${result.data}")
                    isLoginSuccessState = true
                    loginButtonClickToggle++
                    jwtTokenInputUseCase.invoke(result.data)
                    emailInputUseCase.invoke(textIdState)
                }

                else -> {
                    isLoginSuccessState = false
                    loginButtonClickToggle++
                    Log.e(TAG, "login에 실패하였습니다. $isLoginSuccessState 랑 $loginButtonClickToggle")
                }
            }
        }

    }
}