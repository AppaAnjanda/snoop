package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.request.BaseRequest
import com.appa.snoop.domain.model.BaseModel

// TODO("DTO -> Domain or Domain -> DTO 매핑 클래스")

internal fun BaseRequest.toModel(): BaseModel =
    BaseModel(
        base = base
    )

internal fun BaseModel.toDto(): BaseRequest =
    BaseRequest(
        base = base
    )
