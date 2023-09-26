package com.appa.snoop.presentation.ui.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.usecase.category.GetProductListByCategoryUseCase
import com.appa.snoop.domain.usecase.category.GetProductListByKeywordUseCase
import com.appa.snoop.presentation.ui.category.utils.ProductCategoryPagingDataSource
import com.appa.snoop.presentation.ui.category.utils.ProductKeywordPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김희웅] CategoryViewModel"
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase,
    private val getProductListByKeywordUseCase: GetProductListByKeywordUseCase
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

//    private val _productList = MutableStateFlow<List<Product>>(emptyList())
//    val productList = _productList.asStateFlow()

//    fun getProductListByCategory(majorName: String, minorName: String, pageNum: Int) {
//        viewModelScope.launch {
//            val result = getProductListByCategoryUseCase.invoke(majorName, minorName, pageNum)
//
//            when(result) {
//                is NetworkResult.Success -> {
//                    _productList.emit(result.data.contents)
//                    Log.d(TAG, "getProductListByCategory: ${result.data}")
//                }
//                else -> {
//                    Log.d(TAG, "getProductListByCategory: 리스트를 불러오는데 실패했습니다. 네트워크 활성화를 확인하세요.")
//                    Log.d(TAG, "getProductListByCategory: $result")
//                }
//            }
//        }
//    }

    // 페이징
    val _pagingDataFlow = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    fun getProductListByCategoryPaging(majorName: String, minorName: String) {
        viewModelScope.launch {
            getProductListPagingDataByCategory(majorName, minorName)
                .collectLatest { pagingData ->
                    _pagingDataFlow.emit(pagingData)
                }
        }
    }

    fun getProductListByKeywordPaging(keyword: String) {
        viewModelScope.launch {
            getProductListPagingDataByKeyword(keyword)
                .collectLatest { pagingData ->
                    _pagingDataFlow.emit(pagingData)
                }
        }
    }

    fun getProductListPagingDataByCategory(majorName: String, minorName: String): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ProductCategoryPagingDataSource(getProductListByCategoryUseCase, majorName, minorName)
        }.flow.cachedIn(viewModelScope)
    }

    fun getProductListPagingDataByKeyword(keyword: String): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ProductKeywordPagingDataSource(getProductListByKeywordUseCase, keyoword = keyword)
        }.flow.cachedIn(viewModelScope)
    }
}