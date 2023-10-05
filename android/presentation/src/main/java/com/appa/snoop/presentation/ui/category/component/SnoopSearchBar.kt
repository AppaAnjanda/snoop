package com.appa.snoop.presentation.ui.category.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusTargetModifierNode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.main.MainViewModel
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import com.appa.snoop.presentation.util.rememberImeState
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

private const val TAG = "[김희웅] SnoopSearchBar"
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun SnoopSearchBar(
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    categoryViewModel: CategoryViewModel,
    mainViewModel: MainViewModel,
    showSnackBar: (String) -> Unit,
    onSearching: () -> Unit
) {
    val imeState by rememberImeState()

//    LaunchedEffect(Unit) {
//        categoryViewModel.getSearchHistory()
//    }

    LaunchedEffect(categoryViewModel.deleteState, imeState) {
        if (imeState) {
            categoryViewModel.getSearchHistory()
        }
    }

//    LaunchedEffect(key1 = , block = )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SearchBarTextField(
            onValueChange = {
                categoryViewModel.setTextSearch(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.sdp, end = 16.sdp),
            title = "검색어를 입력해주세요.",
            text = categoryViewModel.textSearchState,
            keyboardActions = KeyboardActions(onDone = {
                onSearching()
            }),
            onIconClick = {
                onSearching()
            }
        )
        AnimatedContent(
            targetState = imeState,
            transitionSpec = {
                scaleIn(
                    transformOrigin = TransformOrigin(0.5f, -1f)
                ) togetherWith scaleOut(
                    transformOrigin = TransformOrigin(0.5f, -1f)
                )
            },
            label = ""
        ) {imeState ->
            if (mainViewModel.loginState) {
                if (imeState && categoryViewModel.searchHistoryList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.sdp, start = 16.sdp, end = 16.sdp),
                    ) {
                        items(
                            categoryViewModel.searchHistoryList.size
                        ) {
                            SearchHistoryItem(
                                name = categoryViewModel.searchHistoryList[it],
                                categoryViewModel = categoryViewModel
                            )
                        }
                    }
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.sdp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "로그인하면 검색기록을 볼 수 있어요!",
                        fontSize = 12.ssp
                    )
                }
            }
        }
    }
}