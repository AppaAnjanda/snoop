package com.appa.snoop.presentation.ui.category.component

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import ir.kaaveh.sdpcompose.sdp

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SnoopSearchBar(
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    categoryViewModel: CategoryViewModel,
    showSnackBar: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SearchBarTextField(
            onValueChange = {
                categoryViewModel.setTextSearch(it)
            },
            focusManager = focusManager,
            modifier = Modifier
                .padding(bottom = 16.sdp, start = 16.sdp, end = 16.sdp),
            title = "검색어를 입력해주세요.",
            text = categoryViewModel.textSearchState,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                showSnackBar(categoryViewModel.textSearchState)
            })
        )
    }
}