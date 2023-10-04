package com.appa.snoop.presentation.ui.product.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import com.appa.snoop.domain.model.category.Product
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

fun kakaoShare(product: Product, context: Context) {
    val apiKey = BuildConfig.API_KEY
    val defaultFeed = FeedTemplate(
        content = Content(
            title = product.productName,
            description = product.price.toString(),
            imageUrl = product.productImage,
            link = Link(
                webUrl = product.productLink,
                mobileWebUrl = product.productLink,
            )
        ),
        buttons = listOf(
            Button(
                "웹으로 보기",
                Link(
                    webUrl = product.productLink,
                    mobileWebUrl = product.productLink
                )
            ),
            Button(
                "앱으로 보기",
                Link(
                    webUrl = "kakao$apiKey://kakaolink",
                    mobileWebUrl = "kakao$apiKey://kakaolink",
                    androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                    iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                )
            )
        ),
        buttonTitle = "기웃기웃 테스트"
    )

    // 카카오톡 설치여부 확인
    if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
        // 카카오톡으로 카카오톡 공유 가능
        ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
            if (error != null) {
                Log.e("KAKAO SHARE", "카카오톡 공유 실패", error)
            } else if (sharingResult != null) {
                Log.d("KAKAO SHARE", "카카오톡 공유 성공 ${sharingResult.intent}")
                context.startActivity(sharingResult.intent)

                // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w("KAKAO SHARE", "Warning Msg: ${sharingResult.warningMsg}")
                Log.w("KAKAO SHARE", "Argument Msg: ${sharingResult.argumentMsg}")
            }
        }
    } else {
        // 카카오톡 미설치: 웹 공유 사용 권장
        // 웹 공유 예시 코드
        val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

        // CustomTabs으로 웹 브라우저 열기

        // 1. CustomTabsServiceConnection 지원 브라우저 열기
        // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
        try {
            KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
        } catch (e: UnsupportedOperationException) {
            // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
        }

        // 2. CustomTabsServiceConnection 미지원 브라우저 열기
        // ex) 다음, 네이버 등
        try {
            KakaoCustomTabsClient.open(context, sharerUrl)
        } catch (e: ActivityNotFoundException) {
            // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
        }
    }
}