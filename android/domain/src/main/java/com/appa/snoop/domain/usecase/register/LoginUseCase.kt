package com.appa.snoop.domain.usecase.register

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.JwtAccessToken
import com.appa.snoop.domain.model.member.LoginInfo
import com.appa.snoop.domain.repository.RegisterRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(loginInfo: LoginInfo) : NetworkResult<JwtAccessToken> {
        return registerRepository.login(loginInfo)
    }
}