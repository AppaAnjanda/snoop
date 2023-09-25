package com.appa.snoop.presentation.util

import android.Manifest
import android.os.Build

object PermissionUtils {
    val GALLERY_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
        )
    } else {
        listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}