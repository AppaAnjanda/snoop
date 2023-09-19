package com.appa.snoop.domain.usecase.register

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone
import com.appa.snoop.domain.repository.RegisterRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val registerRepository : RegisterRepository
) {
    suspend operator fun invoke(register: Register) : NetworkResult<RegisterDone> {
        return registerRepository.registerMember(register)
    }
}