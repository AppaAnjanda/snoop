package com.appa.snoop.presentation.ui.category

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.usecase.category.GetProductListByCategoryUseCate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김희웅] CategoryViewModel"
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductListByCategoryUseCate: GetProductListByCategoryUseCate
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

    var digitalCategoryState by mutableStateOf(false)
        private set
    fun digitalCategoryToggle() {
        digitalCategoryState = !digitalCategoryState
    }
    var furnitureCategoryState by mutableStateOf(false)
        private set
    fun furnitureCategoryToggle() {
        furnitureCategoryState = !furnitureCategoryState
    }
    var necessariesCategoryState by mutableStateOf(false)
        private set
    fun necessariesCategoryToggle() {
        necessariesCategoryState = !necessariesCategoryState
    }
    var foodCategoryState by mutableStateOf(false)
        private set
    fun foodCategoryToggle() {
        foodCategoryState = !foodCategoryState
    }
    fun sheetDismiss() {
        if (digitalCategoryState) {
            digitalCategoryToggle()
        }
        if (furnitureCategoryState) {
            furnitureCategoryToggle()
        }
        if (necessariesCategoryState) {
            necessariesCategoryToggle()
        }
        if (foodCategoryState) {
            foodCategoryToggle()
        }
    }

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList = _productList.asStateFlow()

    fun getProductListByCategory(majorName: String, minorName: String) {
        viewModelScope.launch {
            val result = getProductListByCategoryUseCate.invoke(majorName, minorName)
            
            when(result) {
                is NetworkResult.Success -> {
                    _productList.emit(result.data)
                    Log.d(TAG, "getProductListByCategory: ${result.data}")
                }
                else -> {
                    Log.d(TAG, "getProductListByCategory: 리스트를 불러오는데 실패했습니다.")
                }
            }
        }
    }
}