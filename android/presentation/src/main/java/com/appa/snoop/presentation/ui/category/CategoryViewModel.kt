package com.appa.snoop.presentation.ui.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

) : ViewModel() {
    var searchBarState by mutableStateOf(false)
        private set

    // 검색창을 여닫기 위함
    fun searchBarToggle() {
        searchBarState = !searchBarState
    }

    var textSearchState by mutableStateOf("")
        private set

    fun setTextSearch(str: String) {
        textSearchState = str
        when {

        }
    }
}