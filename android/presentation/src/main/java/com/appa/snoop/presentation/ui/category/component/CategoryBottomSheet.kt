package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appa.snoop.domain.model.category.CategoryItem
import com.appa.snoop.presentation.ui.category.CategoryList
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.ui.theme.DarkGrayColor
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    categoryViewModel: CategoryViewModel,
    sheetState: SheetState,
    categoryList: List<CategoryItem> = CategoryList.list,
    onDismiss: () -> Unit
) {
    val scrollableState = rememberScrollState()

    ModalBottomSheet(
//        modifier = Modifier
//            .verticalScroll(scrollableState),
        sheetState = sheetState,
        onDismissRequest = {
            categoryViewModel.sheetDismiss()
            onDismiss()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
//                .verticalScroll(scrollableState)
        ) {
            for (it in categoryList) {
                BottomSheetItem(
                    majorName = it.majorName,
                    categoryViewModel = categoryViewModel,
//                    categoryState = categoryViewModel.firstCategoryState,
                    categoryState = when(it.majorName) {
                        "디지털가전" -> categoryViewModel.digitalCategoryState
                        "가구" -> categoryViewModel.furnitureCategoryState
                        "생활용품" -> categoryViewModel.necessariesCategoryState
                        "식품" -> categoryViewModel.foodCategoryState
                        else -> false
                   },
                    minorList = it.minorList,
                    onClick = {
                        when(it.majorName) {
                            "디지털가전" -> {
                                categoryViewModel.digitalCategoryToggle()
                            }
                            "가구" -> {
                                categoryViewModel.furnitureCategoryToggle()
                            }
                            "생활용품" -> {
                                categoryViewModel.necessariesCategoryToggle()
                            }
                            "식품" -> {
                                categoryViewModel.foodCategoryToggle()
                            }
                        }
                    },
                    onDismiss = {
                        onDismiss()
                    }
                )
//                HorizontalDivider(
//                    modifier = Modifier
//                        .padding(horizontal = 16.sdp),
//                    thickness = 1.dp,
//                    color = DarkGrayColor
//                )
            }
        }
    }
}