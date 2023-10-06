package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.appa.snoop.presentation.ui.category.CategoryList
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.main.MainViewModel
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    categoryViewModel: CategoryViewModel,
    mainViewModel: MainViewModel,
    navController: NavController,
    sheetState: SheetState,
    snackState: SnackbarHostState,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
        ) {
            for (it in CategoryList.list) {
                BottomSheetItem(
                    majorName = it.majorName,
                    categoryViewModel = categoryViewModel,
                    mainViewModel = mainViewModel,
                    categoryState = when(it.majorName) {
                        "디지털가전" -> categoryViewModel.digitalCategoryState
                        "가구" -> categoryViewModel.furnitureCategoryState
                        "생활용품" -> categoryViewModel.necessariesCategoryState
                        "식품" -> categoryViewModel.foodCategoryState
                        else -> false
                    },
                    minorList = it.minorList,
                    onClick = {
                        categoryViewModel.sheetSelect(it.majorName)
                    },
                    onDismiss = {
                        scope.launch {
                            onDismiss()
                            sheetState.hide()
                        }
                    },
                    navController = navController,
                    showSnackbar = {
                        scope.launch {
                            snackState.showSnackbar(it)
                        }
                    }
                )
            }
        }
    }
}