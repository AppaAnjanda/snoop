package com.appa.snoop.presentation.ui.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.category.ProductPaging
import com.appa.snoop.domain.usecase.category.DeleteSearchHistoryUseCase
import com.appa.snoop.domain.usecase.category.GetProductListByCategoryUseCase
import com.appa.snoop.domain.usecase.category.GetProductListByKeywordUseCase
import com.appa.snoop.domain.usecase.category.GetSearchHistoryUseCase
import com.appa.snoop.domain.usecase.category.PostWishToggleUseCase
import com.appa.snoop.domain.usecase.register.GetLoginStatusUseCase
import com.appa.snoop.presentation.ui.category.utils.ProductCategoryPagingDataSource
import com.appa.snoop.presentation.ui.category.utils.ProductKeywordPagingDataSource
import com.appa.snoop.presentation.util.PriceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김희웅] CategoryViewModel"
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductListByCategoryUseCase: GetProductListByCategoryUseCase,
    private val getProductListByKeywordUseCase: GetProductListByKeywordUseCase,
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
    private val postWishToggleUseCase: PostWishToggleUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val MIN_PRICE = 0
        private const val MAX_PRICE = 99999999
    }

    var textSearchState by mutableStateOf("")
        private set

    fun setTextSearch(str: String) {
        textSearchState = str
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

    var keywordSearchState by mutableStateOf(0)
        private set

    fun keywordSearchClick() {
        keywordSearchState++
        Log.d(TAG, "keywordSearchClick: $keywordSearchState")
    }

    // 페이징
    val _pagingDataFlow = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    fun getProductListByCategoryPaging(
        majorName: String,
        minorName: String,
        minPrice: Int = if (!priceRangeState) MIN_PRICE else PriceUtil.parseFormattedPrice(minPriceTextState),
        maxPrice: Int = if (!priceRangeState) MAX_PRICE else PriceUtil.parseFormattedPrice(maxPriceTextState)
    ) {
        viewModelScope.launch {
            getProductListPagingDataByCategory(
                majorName,
                minorName,
                minPrice,
                maxPrice
            ).collectLatest { pagingData ->
                _pagingDataFlow.emit(pagingData)
            }
        }
    }

    fun getProductListByKeywordPaging(
        keyword: String,
        minPrice: Int = if (!priceRangeState) MIN_PRICE else PriceUtil.parseFormattedPrice(minPriceTextState),
        maxPrice: Int = if (!priceRangeState) MAX_PRICE else PriceUtil.parseFormattedPrice(maxPriceTextState)
    ) {
        viewModelScope.launch {
            getProductListPagingDataByKeyword(
                keyword,
                minPrice,
                maxPrice
            ).collectLatest { pagingData ->
                _pagingDataFlow.emit(pagingData)
            }
        }
    }

    fun getProductListPagingDataByCategory(
        majorName: String,
        minorName: String,
        minPrice: Int,
        maxPrice: Int
    ): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ProductCategoryPagingDataSource(
                getProductListByCategoryUseCase,
                majorName = majorName,
                minorName = minorName,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getProductListPagingDataByKeyword(
        keyword: String,
        minPrice: Int,
        maxPrice: Int
    ): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ProductKeywordPagingDataSource(
                getProductListByKeywordUseCase,
                keyoword = keyword,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
//            { keywordSearchClick() }
        }.flow.cachedIn(viewModelScope)
    }

    // 가격 범위
    var minPriceTextState by mutableStateOf("$MIN_PRICE")
        private set
    var maxPriceTextState by mutableStateOf("$MAX_PRICE")
        private set
    fun setMinPriceText(price: String) {
        minPriceTextState = price
    }
    fun setMaxPriceText(price: String) {
        maxPriceTextState = price
    }

    // 가격 범위 정하기 visible toggle
    var priceRangeState by mutableStateOf(false)
        private set
    fun priceRangeStateToggle() {
        priceRangeState = !priceRangeState
    }

    // 로그인 유뮤 판단
    suspend fun isLogined() = getLoginStatusUseCase.invoke()

    // 위시리스트 담기 기능
    private val _wishToggleState = MutableStateFlow<Int>(0)
    val wishToggleState = _wishToggleState.asStateFlow()

//    fun postWishToggle(productCode: String) {
//        viewModelScope.launch {
//            val result = postWishToggleUseCase.invoke(productCode)
//
//            when(result) {
//                is NetworkResult.Success -> {
//                    if (result.data.wishYn) {
//                        _wishToggleState.value++
//                    }
//                    Log.d(TAG, "postWishToggle: 위시리스트 토글 api통신 정상 처리 입니다. 현재 토글 값? -> ${result.data.wishYn}")
//                }
//                else -> {
//                    Log.d(TAG, "postWishToggle: 위시리스트 토글 api통신 오류입니다.")
//                }
//            }
//        }
//    }

    suspend fun toggled(productCode: String) = postWishToggleUseCase.invoke(productCode)

    // 검색기록
    var searchHistoryList by mutableStateOf(listOf<String>())
        private set
    fun getSearchHistory() {
        viewModelScope.launch() {
            val result = getSearchHistoryUseCase.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    searchHistoryList = result.data
                }
                else -> {
                    Log.e(TAG, "getSearchHistory: 검색기록 가져오기 통신 오류입니다.")
                }
            }
        }
    }

    fun deleteHistory(keyword: String) {
        viewModelScope.launch {
            val result = deleteSearchHistoryUseCase.invoke(keyword)

            when (result) {
                is NetworkResult.Success -> {
//                    getSearchHistory()
//                    delete()
                }
                else -> {
                    Log.e(TAG, "deleteHistory: 검색기록 삭제 통신 오류입니다.")
                }
            }
        }
    }

    var deleteState by mutableStateOf(0)
        private set
    fun delete() {
        deleteState++
    }
}