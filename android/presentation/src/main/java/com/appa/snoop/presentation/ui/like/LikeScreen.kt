package com.appa.snoop.presentation.ui.like

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.presentation.ui.like.component.LikeItem
import com.appa.snoop.presentation.ui.like.component.SelelctComponent
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LikeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    MainLaunchedEffect(navController)
    var itemList = listOf(Item(), Item(), Item(), Item())

    // 전체 아이템의 체크 상태를 저장하는 리스트
    val numberOfItems = itemList.size
    var checkedStates by remember { mutableStateOf(List(numberOfItems) { false }) }

    // '모두 선택' 체크박스의 상태
    var allSelected by remember { mutableStateOf(false) }

    // '모두 선택' 체크박스 상태 변경
    fun toggleSelectAll() {
        allSelected = !allSelected
        checkedStates = List(numberOfItems) { allSelected }
    }

    // 개별 체크박스 상태 변경
    fun toggleIndividualSelection(index: Int) {
        checkedStates = checkedStates.toMutableList().also {
            it[index] = !it[index]
        }
        allSelected = checkedStates.all { it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
    ) {
        SelelctComponent(allSelected,
            onChangeCheckedState = { toggleSelectAll() }
        )
        HorizontalDivider(thickness = 1.sdp, color = BackgroundColor2)
        LazyColumn {
            item {
                HorizontalDivider(thickness = 6.sdp, color = BackgroundColor2)
            }
            itemsIndexed(checkedStates) { index, isChecked ->
                LikeItem(itemList[index], isChecked, onCheckedChange = { toggleIndividualSelection(index) },
                    onClick = { /* TODO delete */ })
                HorizontalDivider(color = BackgroundColor2)
            }
        }
    }
}


@Preview
@Composable
fun LikeScreenPreview() {
    LikeScreen(navController = rememberNavController())
}

data class Item(
    var name: String = "Apple 맥북 프로 14 스페이스 그레이 M2 pro 10코어",
    var img: String = "https://media.istockphoto.com/id/1358386001/photo/apple-macbook-pro.jpg?s=612x612&w=0&k=20&c=d14HA-i0EHpdvNvccdJQ5pAkQt8bahxjjb6fO6hs4E8=",
    var currentPrice: Int = 2620000,
    var goalPrice: Int = 2000000
)