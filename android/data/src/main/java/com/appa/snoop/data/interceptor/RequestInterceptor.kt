package com.appa.snoop.data.interceptor

import android.util.Log
import com.appa.snoop.data.local.PreferenceDataSource
import com.appa.snoop.data.local.PreferenceDataSource.Companion.ACCESS_TOKEN
//import com.ssafy.templateapplication.ApplicationClass.Companion.sharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "[김희웅] RequestInterceptor"
class RequestInterceptor @Inject constructor(
    private val prefs: PreferenceDataSource
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        Log.d(TAG, "intercept RequestInterceptor: ${prefs.getString(ACCESS_TOKEN)}")
        try {
            prefs.getString(ACCESS_TOKEN).let { token ->
                token?.let {
                    builder.addHeader("Authorization", "Bearer $token")
                    Log.d(TAG, "intercept: JWT AccessToken 헤더에 담았습니다.")
                    return chain.proceed(builder.build())
                }
            }
        } catch (e: Exception) {
            return chain.proceed(chain.request())
        }
        return chain.proceed(chain.request())
    }

}
