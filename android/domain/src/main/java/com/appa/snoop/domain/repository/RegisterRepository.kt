package com.appa.snoop.domain.repository

import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.member.Register
import com.appa.snoop.domain.model.member.RegisterDone
import java.lang.reflect.Member

interface RegisterRepository {
    // 회원가입
    suspend fun registerMember(register: Register) : NetworkResult<RegisterDone>
}