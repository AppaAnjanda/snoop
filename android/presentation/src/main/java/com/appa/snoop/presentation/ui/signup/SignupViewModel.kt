package com.appa.snoop.presentation.ui.signup

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(

): ViewModel() {
    //TODO 읽고있다가 값에 변경이 있으면 버튼 색 바꿔주고, 이메일 칸 채워주는 코드 필요
    private var _isLoginSuccess by
        mutableStateOf<Boolean>(false)
    val isLoginSuccess = _isLoginSuccess

    private var _kakaoEmail by
        mutableStateOf<String>("")
    val kakaoEmail = _kakaoEmail

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                Log.e("[김희웅] Kakao callback: ", "카카오 계정 로그인 실패", error)
            }
            token != null -> {
                // 카카오 로그인이 성공했으면
                _isLoginSuccess = true
                getKakaoEmail()
            }
        }
    }

    fun kakaoLogin(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡 설치시
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) { // 로그인 실패한 경우
                    Log.e("[김희웅] Kakao: ", "카카오톡 로그인 실패 (카카오톡이 설치되어있음)", error)
                }
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 위의 에러 종류가 아니라면
                UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
            }
        } else {
            // 카카오톡 미설치시
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    }

    private fun getKakaoEmail() {
        UserApiClient.instance.me { user, error ->
            when {
                error != null -> {
                    Log.e("[김희웅] Kakao: ", "사용자 정보 실패", error)
                }
                user != null -> {
                    val email = user.kakaoAccount?.email.orEmpty()

                    _kakaoEmail = email

                    Log.d("[김희웅] Kakao: ", "받아온 카카오 이메일? => $email")
                    UserApiClient.instance.logout {
                        Log.d("[김희웅] Kakao: ", "로그아웃 완료")
                    }
                }
            }
        }
    }
}