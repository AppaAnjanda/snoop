package com.appa.snoop.presentation.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.model.product.Timing
import com.appa.snoop.domain.usecase.product.GetProductDetailUseCase
import com.appa.snoop.domain.usecase.product.GetProductTimingUseCase
import com.appa.snoop.domain.usecase.product.RefreshProductUseCase
import com.appa.snoop.presentation.util.UrlUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import javax.inject.Inject

private const val TAG = "[김진영] ProductViewModel"

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getProductTimingUseCase: GetProductTimingUseCase,
    private val refreshProductUseCase: RefreshProductUseCase
) : ViewModel() {
    private val _productState =
        MutableStateFlow(Product("", "", "", "", "", 0, "", "", "", "", false))
    var productState: StateFlow<Product> = _productState.asStateFlow()

    private val _timingState =
        MutableStateFlow(Timing(0, 0, 0.0, ""))
    var timingState: StateFlow<Timing> = _timingState.asStateFlow()

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
            // url 인코딩해서 api 호출
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
}