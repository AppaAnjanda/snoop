package com.appa.snoop.data.mapper

import com.appa.snoop.data.model.product.response.TimingResponse
import com.appa.snoop.domain.model.product.Timing

fun TimingResponse.toDomain(): Timing {
    return Timing(
        avgPrice = avgPrice,
        curPrice = curPrice,
        diffPercent = diffPercent,
        timing = timing
    )
}