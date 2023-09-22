package com.appa.snoop.domain.usecase.register

import com.appa.snoop.domain.model.member.JwtTokens
import com.appa.snoop.domain.repository.RegisterRepository
import javax.inject.Inject

class GetLoginStatusUseCase @Inject constructor(
    private val registerRepository : RegisterRepository
) {
    suspend operator fun invoke(): JwtTokens {
        return registerRepository.getLoginStatus()
    }
}
