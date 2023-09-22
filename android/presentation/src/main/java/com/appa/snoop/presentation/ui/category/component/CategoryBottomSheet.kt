package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.mypage.MyPageViewModel
import com.appa.snoop.presentation.ui.mypage.component.CheckBoxRow
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    categoryViewModel: CategoryViewModel,
    sheetState: SheetState,
    categoryList: List<String> = listOf("디지털/가전", "가구", "생활용품", "식품"),
    onDismiss: () -> Unit
) {
    val scrollableState = rememberScrollState()

    ModalBottomSheet(
//        modifier = Modifier
//            .verticalScroll(scrollableState),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
//                .verticalScroll(scrollableState)
        ) {
            BottomSheetItem(
                majorName = "test",
                categoryViewModel = categoryViewModel,
                categoryState = categoryViewModel.firstCategoryState,
                onClick = {

                }
            )
            for (it in categoryList) {
                BottomSheetItem(
                    majorName = it,
                    categoryViewModel = categoryViewModel,
//                    categoryState = categoryViewModel.firstCategoryState,
                    categoryState = false,
                    onClick = {

                    }
                )
            }
        }
    }
}