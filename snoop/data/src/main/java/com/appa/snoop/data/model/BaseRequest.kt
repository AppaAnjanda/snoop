package com.appa.snoop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseRequest(
    val base: Int
): Parcelable