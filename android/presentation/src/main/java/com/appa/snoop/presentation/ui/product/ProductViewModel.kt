package com.appa.snoop.presentation.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.product.AlertPrice
import com.appa.snoop.domain.model.product.GraphItem
import com.appa.snoop.domain.model.product.Timing
import com.appa.snoop.domain.model.product.WishProduct
import com.appa.snoop.domain.model.wishbox.WishBox
import com.appa.snoop.domain.usecase.product.GetProductDetailUseCase
import com.appa.snoop.domain.usecase.product.GetProductGraphUseCase
import com.appa.snoop.domain.usecase.product.GetProductTimingUseCase
import com.appa.snoop.domain.usecase.product.GetRecommendProductUseCase
import com.appa.snoop.domain.usecase.product.RefreshProductUseCase
import com.appa.snoop.domain.usecase.product.RegistWishProductUseCase
import com.appa.snoop.presentation.util.UrlUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김진영] ProductViewModel"

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getProductTimingUseCase: GetProductTimingUseCase,
    private val refreshProductUseCase: RefreshProductUseCase,
    private val getRecommendProductUseCase: GetRecommendProductUseCase,
    private val getProductGraphUseCase: GetProductGraphUseCase,
    private val registWishProductUseCase: RegistWishProductUseCase
) : ViewModel() {

    private val _productState =
        MutableStateFlow(Product("", "", "", "", "", 0, "", "", "", "", false, false))
    var productState: StateFlow<Product> = _productState.asStateFlow()

    private val _timingState =
        MutableStateFlow(Timing(0, 0, 0.0, ""))
    var timingState: StateFlow<Timing> = _timingState.asStateFlow()

    private val _recommendProductState =
        MutableStateFlow(listOf(Product("", "", "", "", "", 0, "", "", "", "", false, false)))
    var recommendProductState: StateFlow<List<Product>> = _recommendProductState.asStateFlow()

    private val _productGraphState =
        MutableStateFlow(listOf(GraphItem("", 0)))
    var productGraphState: StateFlow<List<GraphItem>> = _productGraphState.asStateFlow()

    private val _wishState =
        MutableStateFlow(WishProduct(0, false, "", "", 0))
    var wishState: StateFlow<WishProduct> = _wishState.asStateFlow()

    fun getProductDetail(productCode: String) {
        viewModelScope.launch {
            // url 인코딩해서 api 호출
            val encoder = UrlUtil.encodeProductCode(productCode = productCode)
            val result = getProductDetailUseCase.invoke(encoder)

            when (result) {
                is NetworkResult.Success -> {
                    _productState.emit(result.data)
                    Log.d(TAG, "getProductDetail: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getProductDetail: 상품 상세 조회 실패")
                }
            }
        }
    }

    fun getProductTiming(productCode: String) {
        viewModelScope.launch {
            val encoder = UrlUtil.encodeProductCode(productCode = productCode)
            val result = getProductTimingUseCase.invoke(encoder)

            when (result) {
                is NetworkResult.Success -> {
                    _timingState.emit(result.data)
                    Log.d(TAG, "getProductTiming: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getProductTiming: 상품 타이밍 조회 실패")
                }
            }
        }
    }

        fun getRecommendProduct(productCode: String) {
            viewModelScope.launch {
                val encoder = UrlUtil.encodeProductCode(productCode = productCode)
                val result = getRecommendProductUseCase.invoke(encoder)

                when (result) {
                    is NetworkResult.Success -> {
                        _recommendProductState.emit(result.data)
                        Log.d(TAG, "getRecommendProduct: ${result.data}")
                    }

                    else -> {
                        Log.d(TAG, "getRecommendProduct: 추천 상품 조회 실패")
                    }
                }
            }
        }

    fun getProductGraph(productCode: String, period: String) {
        viewModelScope.launch {
            val encoder = UrlUtil.encodeProductCode(productCode = productCode)
            val result = getProductGraphUseCase.invoke(productCode, period)

            when (result) {
                is NetworkResult.Success -> {
                    _productGraphState.emit(result.data)
                    Log.d(TAG, "getProductGraph: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getProductGraph: 상품 그래프 조회 실패")
                }
            }
        }
    }

    fun registWishProduct(productCode: String, price: Int) {
        viewModelScope.launch {
            val encoder = UrlUtil.encodeProductCode(productCode = productCode)
            val result = registWishProductUseCase.invoke(encoder, AlertPrice(price))

            when (result) {
                is NetworkResult.Success -> {
                    _wishState.emit(result.data)
                    Log.d(TAG, "registWishProduct: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "registWishProduct: 상품 찜 등록 실패")
                }
            }
        }
    }
}