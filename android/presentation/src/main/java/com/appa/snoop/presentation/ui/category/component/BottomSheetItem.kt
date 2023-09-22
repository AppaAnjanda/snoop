package com.appa.snoop.presentation.ui.category.component

import android.annotation.SuppressLint
import android.content.res.loader.ResourcesProvider
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.mypage.component.CheckBoxRow
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun BottomSheetItem(
    majorName: String = "대분류",
    minorList: List<String> = listOf("소분류", "소분류", "소분류", "소분류", "소분류"),
    categoryViewModel: CategoryViewModel,
    categoryState: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.sdp)
            .clickable { categoryViewModel.firstCategoryToggle() },
//            .background(BackgroundColor)
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = if (categoryState) painterResource(id = R.drawable.ic_arrow_down) else painterResource(id = R.drawable.ic_arrow_right), // 벡터 이미지 리소스
            contentDescription = null, // 이미지 설명 (접근성을 위해 필요)
            modifier = Modifier
                .size(16.sdp), // 이미지 크기 설정
        )
        Text(
            text = majorName,
            fontSize = 14.ssp
        )
    }
//    Spacer(modifier = Modifier.height(4.sdp))
    AnimatedContent(
        targetState = categoryState,
        transitionSpec = {
            slideInVertically(
                initialOffsetY = { -200 },
                animationSpec = tween(200)
            ) togetherWith slideOutVertically (
                targetOffsetY = { -200 },
                animationSpec = tween(200)
            ) using SizeTransform(false)
        },
        label = ""
    ) {
        if (it) {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 12.sdp)
            ) {
                items(minorList) {minorName ->
//                    AnimatedContent(
//                        targetState = categoryState,
//                        transitionSpec = {
//                            slideInVertically(
//                                initialOffsetY = { -100 },
//                                animationSpec = tween(200)
//                            ) togetherWith slideOutVertically (
//                                targetOffsetY = { -100 },
//                                animationSpec = tween(200)
//                            ) using SizeTransform(false)
//                        },
//                        label = ""
//                    ) {
//                        Text(
//                            text = minorName,
//                            fontSize = 12.ssp
//                        )
//                        Spacer(modifier = Modifier.height(4.sdp))
//                    }
                    Text(
                        text = minorName,
                        fontSize = 12.ssp
                    )
                    Spacer(modifier = Modifier.height(4.sdp))
                }
            }
        }
    }
//    if (categoryState) {
//        LazyColumn(
//            modifier = Modifier
//                .padding(start = 12.sdp)
//        ) {
//            items(minorList) {
//                Text(
//                    text = it,
//                    fontSize = 12.ssp
//                )
//                Spacer(modifier = Modifier.height(4.sdp))
//            }
//        }
//    }
}