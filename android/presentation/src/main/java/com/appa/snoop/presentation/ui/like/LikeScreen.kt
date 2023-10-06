package com.appa.snoop.presentation.ui.like

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.domain.model.wishbox.WishBoxDeleteList
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.navigation.Router
import com.appa.snoop.presentation.ui.like.component.LikeItem
import com.appa.snoop.presentation.ui.like.component.SelelctComponent
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.PriceUtil
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "[김진영] LikeScreen"

@Composable
fun LikeScreen(
    navController: NavController,
    showSnackBar: (String) -> Unit,
    likeViewModel: LikeViewModel = hiltViewModel()
) {
    MainLaunchedEffect(navController)
    val focusManager = LocalFocusManager.current

    val wishboxList by likeViewModel.wishboxListState.collectAsState()
    val updatedWishbox by likeViewModel.updateWishboxState.collectAsState()
    // 전체 아이템의 체크 상태를 저장하는 리스트
    var numberOfItems by remember {
        mutableStateOf(0)
    }
    var checkedStates by remember { mutableStateOf(List(numberOfItems) { false }) }

    // '모두 선택' 체크박스의 상태
    var allSelected by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit, key2 = updatedWishbox) {
        likeViewModel.getWishBoxList()
    }

    LaunchedEffect(wishboxList) {
        numberOfItems = wishboxList.size
        checkedStates = List(numberOfItems) { false }
    }

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
            onChangeCheckedState = { toggleSelectAll() },
            onDeletedLikeList = {
                val wishBoxDeleteList = checkedStates.withIndex()
                    .filter { it.value }
                    .map { wishboxList[it.index].wishboxId }
                likeViewModel.deleteWishBoxList(WishBoxDeleteList(wishBoxDeleteList))
            }
        )
        HorizontalDivider(thickness = 1.sdp, color = BackgroundColor2)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HorizontalDivider(thickness = 6.sdp, color = BackgroundColor2)
            }
            if (checkedStates.isNotEmpty() && wishboxList.size == checkedStates.size) {
                itemsIndexed(items = checkedStates) { index, isChecked ->
                    LikeItem(item = wishboxList[index],
                        value = isChecked,
                        focusManager = focusManager,
                        onCheckedChange = { toggleIndividualSelection(index) },
                        onDeleteClick = {
                            likeViewModel.deleteWishBox(wishboxList[index].wishboxId)
                        },
                        onUpdateClick = { price ->
                            likeViewModel.updateWishBoxPrice(
                                wishboxList[index].wishboxId,
                                PriceUtil.parseFormattedPrice(price)
                            )
                        },
                        onItemClick = {
                            val route = Router.CATEGORY_PRODUCT_ROUTER_NAME.replace(
                                "{productCode}",
                                wishboxList[index].productCode
                            )
                            navController.navigate(route)
                        })
                    HorizontalDivider(color = BackgroundColor2)
                }
            }


        }
    }
}


@Preview
@Composable
fun LikeScreenPreview() {
    LikeScreen(navController = rememberNavController(), showSnackBar = {})
}