package com.appa.snoop.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseResponse(
    val base: Int
): Parcelable