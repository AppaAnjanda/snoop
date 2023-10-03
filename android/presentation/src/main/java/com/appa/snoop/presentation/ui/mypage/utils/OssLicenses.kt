package com.appa.snoop.presentation.ui.mypage.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

fun openSourceLicenses(context: Context) {
    OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스 목록") //액티비티 제목 셋팅
    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}