package com.appa.snoop.data.repository

import android.util.Log
import com.appa.snoop.data.local.PreferenceDataSource
import com.appa.snoop.data.local.PreferenceDataSource.Companion.ACCESS_TOKEN
import com.appa.snoop.data.local.PreferenceDataSource.Companion.REFRESH_TOKEN
import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.mapper.toDto
import com.appa.snoop.data.service.RegisterService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone
import com.appa.snoop.domain.repository.RegisterRepository
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "[김희웅] RegisterRepositoryImpl"

class RegisterRepositoryImpl @Inject constructor(
    private val preferenceDatasource: PreferenceDataSource,
    private val registerService: RegisterService
) : RegisterRepository {
    // 회원가입
    override suspend fun registerMember(register: Register): NetworkResult<String> {
        return handleApi { registerService.registerMember(register) }
    }

    // 로그인
    override suspend fun login(loginInfo: LoginInfo): NetworkResult<JwtTokens> {
        return handleApi { registerService.login(loginInfo.toDto()).toDomain() }
    }

    // 토큰 저장
    override suspend fun putJwtTokens(jwtTokens: JwtTokens) {
        preferenceDatasource.putString(ACCESS_TOKEN, jwtTokens.accessToken)
        preferenceDatasource.putString(REFRESH_TOKEN, jwtTokens.refreshToken)
//        preferenceDatasource.putString("refresh_token", jwtAccessToken.refreshToken)
//        preferenceDatasource.putInt("member_id", jwtAccessToken.memberId)
//        preferenceDatasource.putString("role", loginResult.role[0].role)
//        Log.d(TAG, "토큰이 잘 저장되는지 로그 -> access_token : ${preferenceDatasource.getString("access_token")} refresh_token : ${preferenceDatasource.getString("refresh_token")} member_id : ${preferenceDatasource.getInt("member_id")}\nrole: ${preferenceDatasource.getString("role")}")
        Log.d(
            TAG,
            "토큰이 잘 저장되는지 로그 -> access_token : ${preferenceDatasource.getString(ACCESS_TOKEN)}"
        )
        Log.d(
            TAG,
            "토큰이 잘 저장되는지 로그 -> refresh_token : ${preferenceDatasource.getString(REFRESH_TOKEN)}"
        )
    }

    // 로그인 유무 확인
    override suspend fun getLoginStatus(): JwtTokens {
        var jwtAccessToken = preferenceDatasource.getString(ACCESS_TOKEN) ?: "no_token_error"
        var jwtRefreshToken = preferenceDatasource.getString(REFRESH_TOKEN) ?: "no_refresh_error"
        Log.d(TAG, "getLoginStatus: $jwtAccessToken")
        return JwtTokens(jwtAccessToken, jwtRefreshToken)
    }

    // 로그아웃
    override suspend fun logout() {
        preferenceDatasource.remove("access_token")
    }

    override suspend fun getFcmToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d(TAG, "getFcmToken: Fetching FCM token failed", task.exception)
                    continuation.resume("")
                } else {
                    val token = task.result
                    Log.d(TAG, "getFcmToken: FCM token is $token")
                    continuation.resume(token ?: "")
                }
            }
    }
}
