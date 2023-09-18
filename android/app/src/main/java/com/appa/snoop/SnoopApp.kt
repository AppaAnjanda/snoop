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
        KakaoSdk.init(this, "3c725efcd3941d2679e8199b069e6bd1")
    }
}