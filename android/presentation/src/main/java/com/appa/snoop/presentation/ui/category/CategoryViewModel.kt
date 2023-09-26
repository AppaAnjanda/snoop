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
        private const val MIN_PRICE = 0
        private const val MAX_PRICE = 99999999
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

    fun sheetSelect(categoryName: String) {
        when(categoryName) {
            "디지털가전" -> {
                digitalCategoryToggle()
                furnitureCategoryState = false
                necessariesCategoryState = false
                foodCategoryState = false
            }
            "가구" -> {
                furnitureCategoryToggle()
                digitalCategoryState = false
                necessariesCategoryState = false
                foodCategoryState = false
            }
            "생활용품" -> {
                necessariesCategoryToggle()
                digitalCategoryState = false
                furnitureCategoryState = false
                foodCategoryState = false
            }
            "식품" -> {
                foodCategoryToggle()
                digitalCategoryState = false
                furnitureCategoryState = false
                necessariesCategoryState = false
            }
        }
    }

    var categorySearchState by mutableStateOf(0)
        private set
    var keywordSearchState by mutableStateOf(0)
        private set

    fun categorySearchClick() {
        categorySearchState++
    }
    fun keywordSearchClick() {
        keywordSearchState++
    }

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

    // 가격 범위
    var minPrice by mutableStateOf(MIN_PRICE)
        private set
    var maxPrice by mutableStateOf(MAX_PRICE)
        private set
    fun setPriceRange(min: Int, max: Int) {
        minPrice = min
        maxPrice = max
    }

    // 가격 범위 정하기 visible toggle
    var priceRangeState by mutableStateOf(false)
        private set
    fun priceRangeStateToggle() {
        priceRangeState = !priceRangeState
    }
}