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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.usecase.category.GetProductListByCategoryUseCase
import com.appa.snoop.presentation.ui.category.utils.ProductPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김희웅] CategoryViewModel"
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
    }

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

    fun getProductListByCategory(majorName: String, minorName: String, pageNum: Int) {
        viewModelScope.launch {
            val result = getProductListByCategoryUseCase.invoke(majorName, minorName, pageNum)

            when(result) {
                is NetworkResult.Success -> {
                    _productList.emit(result.data.contents)
                    Log.d(TAG, "getProductListByCategory: ${result.data}")
                }
                else -> {
                    Log.d(TAG, "getProductListByCategory: 리스트를 불러오는데 실패했습니다. 네트워크 활성화를 확인하세요.")
                    Log.d(TAG, "getProductListByCategory: $result")
                }
            }
        }
    }

//    lateinit var products: Flow<PagingData<Product>>

//    fun getProductListByCategoryPaging(majorName: String, minorName: String) {
//        products = getProductListPagingData(majorName, minorName)
//    }

    val _pagingDataFlow = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

//    val pagingFlow: Flow<PagingData<Product>> = getProductListPagingData("디지털가전", "노트북")

    // Collect the paging data into the StateFlow
    fun getProductListByCategoryPaging(majorName: String, minorName: String) {
        viewModelScope.launch {
            getProductListPagingData(majorName, minorName)
                .collectLatest { pagingData ->
                    _pagingDataFlow.emit(pagingData)
                }
//            getProductListByCategoryUseCase.invoke(majorName, minorName, 1)
        }
    }

    fun getProductListPagingData(majorName: String, minorName: String): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ProductPagingDataSource(getProductListByCategoryUseCase, majorName, minorName)
        }.flow.cachedIn(viewModelScope)
    }
}