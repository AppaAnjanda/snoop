package com.appa.snoop.data.interceptor

import android.util.Log
import com.appa.snoop.data.local.PreferenceDataSource
import com.appa.snoop.data.local.PreferenceDataSource.Companion.ACCESS_TOKEN
import com.appa.snoop.data.local.PreferenceDataSource.Companion.BASE_URL
import com.appa.snoop.data.local.PreferenceDataSource.Companion.REFRESH_TOKEN
import com.appa.snoop.data.model.error.response.ErrorResponse
import com.appa.snoop.data.model.registration.request.RefreshTokenRequest
import com.appa.snoop.data.service.RegisterService
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject


/**
 * 서버에 요청할 때 accessToken유효한지 검사
 * 유효하지 않다면 재발급 api 호출
 * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
 */
private const val TAG = "ResponseInterceptor_싸피"
class ResponseInterceptor @Inject constructor(
    private val prefs: PreferenceDataSource
): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        var accessToken = ""
        var isRefreshable = false

        Log.d(TAG, "intercept: 지금 코드 ${response.code}")
        Log.d(TAG, "intercept: 지금 네트워크 리스폰스 ${response.networkResponse}")

        when (response.code) {
            401 -> { // 여러 에러들 종합 (에러 메시지로 확인하자.)
                val errorResponse = parseErrorResponse(response.body)
                Log.d(TAG, "intercept: 에러 바디 파싱 !!!!!!!!!! ${errorResponse}")

                Log.d(TAG, "intercept: 에러(401) : 만료된 토큰입니다.")
                runBlocking {
                    //토큰 갱신 api 호출

                    Log.d(TAG, "intercept: ${prefs.getString(REFRESH_TOKEN)}")
                    prefs.getString(REFRESH_TOKEN)?.let {
                        Log.d(TAG, "intercept: ${prefs.getString(REFRESH_TOKEN)}")

                        val result = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(RegisterService::class.java).poseRefreshToken(
                                RefreshTokenRequest(prefs.getString(REFRESH_TOKEN)!!)
                            )

                        Log.d(
                            TAG, "intercept 현재 찐 refresh: ${
                                prefs.getString(
                                    REFRESH_TOKEN
                                )
                            }"
                        )
                        if (result.isNotEmpty()) {
                            Log.d(TAG, "intercept: 다시 받아오는데 성공했어요!!!!!!")
                            prefs.putString(ACCESS_TOKEN, result)
                            Log.d(TAG, "intercept: 만료된 토큰 다시 받은거 $result")
                            accessToken = result
                            isRefreshable = true
                        }
                        if (result.equals(ErrorResponse(httpStatus = 401, errorCode = "A-005", errorMessage = "해당 refresh token은 존재하지 않습니다."))) {
                            Log.d(TAG, "intercept: 리프레시 토큰으로 다시 받아오는 코드 실패입니다.")
//                                    Log.d(TAG, "intercept success : ${result.isSuccessful}")
//                                    Log.d(TAG, "intercept  : ${result.code()}")
//                                    Log.d(TAG, "intercept: ${result.headers()}")
//                                    Log.d(TAG, "intercept: ${result.message()}")
//                                    Log.d(TAG, "intercept: ${result.errorBody()}")
                            throw (IOException("refresh_exception"))
                        }
                    }
                }
            }
            500 -> {

            }
        }

        // 다시 내가 호출했었던 거 호출하는 로직 필요할듯?
        if(isRefreshable) {
            Log.d(TAG, "intercept: 리프레시가 알맞게 통신했고, 새 엑세스토큰으로 가능하다는 소리입니다~")
            val newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
            return chain.proceed(newRequest)
        }

        return response
    }

    private fun parseErrorResponse(responseBody: ResponseBody?): ErrorResponse {
        val gson = Gson()
        return gson.fromJson(responseBody?.charStream(), ErrorResponse::class.java)
    }
}
