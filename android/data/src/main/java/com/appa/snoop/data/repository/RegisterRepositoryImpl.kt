package com.appa.snoop.data.repository

import android.util.Log
import com.appa.snoop.data.local.PreferenceDataSource
import com.appa.snoop.data.mapper.toDomain
import com.appa.snoop.data.service.RegisterService
import com.appa.snoop.data.service.handleApi
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone
import com.appa.snoop.domain.repository.RegisterRepository
import javax.inject.Inject

private const val TAG = "[김희웅] RegisterRepositoryImpl"
class RegisterRepositoryImpl @Inject constructor(
    private val preferenceDatasource: PreferenceDataSource,
    private val registerService: RegisterService
) : RegisterRepository {
    // 회원가입
    override suspend fun registerMember(register: Register): NetworkResult<RegisterDone> {
        return handleApi { registerService.registerMember(register).toDomain() }
    }
    // 로그인
    override suspend fun login(loginInfo: LoginInfo): NetworkResult<JwtTokens> {
        return handleApi { registerService.login(loginInfo).toDomain() }
    }
    // 토큰 저장
    override suspend fun putJwtTokens(jwtTokens: JwtTokens) {
        preferenceDatasource.putString("access_token", jwtTokens.accessToken)
//        preferenceDatasource.putString("refresh_token", jwtAccessToken.refreshToken)
//        preferenceDatasource.putInt("member_id", jwtAccessToken.memberId)
//        preferenceDatasource.putString("role", loginResult.role[0].role)
//        Log.d(TAG, "토큰이 잘 저장되는지 로그 -> access_token : ${preferenceDatasource.getString("access_token")} refresh_token : ${preferenceDatasource.getString("refresh_token")} member_id : ${preferenceDatasource.getInt("member_id")}\nrole: ${preferenceDatasource.getString("role")}")
        Log.d(TAG, "토큰이 잘 저장되는지 로그 -> access_token : ${preferenceDatasource.getString("access_token")}")
    }
    // 로그인 유무 확인
    override suspend fun getLoginStatus(): JwtTokens {
        var jwtAccessToken = preferenceDatasource.getString("access_token") ?: "no_token_error"
//        var jwtRefreshToken = preferenceDatasource.getString("refresh_token")

        return JwtTokens(jwtAccessToken)
    }
    // 로그아웃
    override suspend fun logout() {
        preferenceDatasource.remove("access_token")
    }
}
