package com.appa.snoop.presentation.util

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object Converter {
    fun getRealPathFromUri(context: Context, contentUri: Uri): String? {
        var result: String? = null
        context.contentResolver.openInputStream(contentUri)?.use { stream ->
            val tempFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(tempFile)
            stream.copyTo(outputStream)
            result = tempFile.absolutePath
        }
        return result
    }

}


