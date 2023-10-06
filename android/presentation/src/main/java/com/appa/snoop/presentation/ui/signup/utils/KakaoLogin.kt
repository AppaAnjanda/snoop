package com.appa.snoop.presentation.ui.signup.utils

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

//val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//    when {
//        error != null -> {
//            Log.e("[김희웅] Kakao callback: ", "카카오 계정 로그인 실패", error)
//        }
//        token != null -> {
//            getKakaoEmail()
//        }
//    }
//}
//
//fun kakaoLogin(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit) {
//    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
//        // 카카오톡 설치시
//        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
//            if (error != null) { // 로그인 실패한 경우
//                Log.e("[김희웅] Kakao: ", "카카오톡 로그인 실패 (카카오톡이 설치되어있음)", error)
//            }
//            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                return@loginWithKakaoTalk
//            }
//
//            // 위의 에러 종류가 아니라면
//            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
//        }
//    } else {
//        // 카카오톡 미설치시
//        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
//    }
//}
//
//fun getKakaoEmail() {
//    UserApiClient.instance.me { user, error ->
//        when {
//            error != null -> {
//                Log.e("[김희웅] Kakao: ", "사용자 정보 실패", error)
//            }
//            user != null -> {
//                val email = user.kakaoAccount?.email.orEmpty()
//                Log.d("[김희웅] Kakao: ", "받아온 카카오 이메일? => $email")
//                UserApiClient.instance.logout {
//                    Log.d("[김희웅] Kakao: ", "로그아웃 완료")
//                }
//            }
//        }
//    }
//}