package com.appa.snoop

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@HiltAndroidApp
class SnoopApp: Application() {
    // 앱이 처음 생성되는 순간, SP를 새로 만들어준다.
    override fun onCreate() {
        super.onCreate()
        getHashKey()
        KakaoSdk.init(this, getString(R.string.NATIVE_APP_KEY))
    }

    // 카카오 로그인을 위해 해시키를 발급합니다.
    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))

            } catch (e: NoSuchAlgorithmException) {
            }
        }
    }
}