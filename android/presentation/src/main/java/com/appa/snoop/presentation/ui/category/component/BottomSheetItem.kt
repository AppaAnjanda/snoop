package com.appa.snoop.presentation.ui.category.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun BottomSheetItem(
    majorName: String = "대분류",
    minorList: List<String>,
    categoryViewModel: CategoryViewModel,
    categoryState: Boolean,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.sdp)
            .clickable { onClick() },
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
    AnimatedContent(
        targetState = categoryState,
        transitionSpec = {
             scaleIn(
                 transformOrigin = TransformOrigin(0.3f, -1f)
             ) togetherWith scaleOut(
                 transformOrigin = TransformOrigin(0.3f, -1f)
             )
        },
        label = ""
    ) {
        if (it) {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 20.sdp)
            ) {
                items(minorList) {minorName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                categoryViewModel.getProductListByCategoryPaging(majorName, minorName)
//                                categoryViewModel.getProductListByCategory(majorName, minorName, 1)
                                onDismiss()
                            },
                    ) {
                        Text(
                            modifier = Modifier,
                            text = minorName,
                            fontSize = 12.ssp,
                        )
                    }
                    Spacer(modifier = Modifier.height(3.sdp))
                    HorizontalDivider(
                        thickness = 1.sdp,
                        color = BackgroundColor2
                    )
                    Spacer(modifier = Modifier.height(3.sdp))
                }
            }
        }
    }
}