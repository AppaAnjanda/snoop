package com.appa.snoop.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.usecase.home.GetPopularDigitalListUseCase
import com.appa.snoop.domain.usecase.home.GetPopularFoodListUseCase
import com.appa.snoop.domain.usecase.home.GetPopularFurnitureListUseCase
import com.appa.snoop.domain.usecase.home.GetPopularNecessariesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김희웅] HomeViewModel"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularDigitalListUseCase: GetPopularDigitalListUseCase,
    private val getPopularNecessariesListUseCase: GetPopularNecessariesListUseCase,
    private val getPopularFurnitureListUseCase: GetPopularFurnitureListUseCase,
    private val getPopularFoodListUseCase: GetPopularFoodListUseCase
) : ViewModel() {

    private val _digitalList = MutableStateFlow<List<Product>>(emptyList())
    val digitalList = _digitalList.asStateFlow()

    private val _necessariesList = MutableStateFlow<List<Product>>(emptyList())
    val necessariesList = _necessariesList.asStateFlow()

    private val _furnitureList = MutableStateFlow<List<Product>>(emptyList())
    val furnitureList = _furnitureList.asStateFlow()

    private val _foodList = MutableStateFlow<List<Product>>(emptyList())
    val foodList = _foodList.asStateFlow()

    fun getPopularDigitalList() {
        viewModelScope.launch {
            val result = getPopularDigitalListUseCase.invoke()

            when(result) {
                is NetworkResult.Success -> {
                    _digitalList.emit(result.data)
                }
                else -> {
                    Log.e(TAG, "디지털 인기 상품 목록 조회 실패")
                }
            }
        }
    }

    fun getPopularNecessariesList() {
        viewModelScope.launch {
            val result = getPopularNecessariesListUseCase.invoke()

            when(result) {
                is NetworkResult.Success -> {
                    _necessariesList.emit(result.data)
                }
                else -> {
                    Log.e(TAG, "생활용품 인기 상품 목록 조회 실패")
                }
            }
        }
    }

    fun getPopularFurnitureList() {
        viewModelScope.launch {
            val result = getPopularFurnitureListUseCase.invoke()

            when(result) {
                is NetworkResult.Success -> {
                    _furnitureList.emit(result.data)
                }
                else -> {
                    Log.e(TAG, "가구 인기 상품 목록 조회 실패")
                }
            }
        }
    }

    fun getPopularFoodList() {
        viewModelScope.launch {
            val result = getPopularFoodListUseCase.invoke()

            when(result) {
                is NetworkResult.Success -> {
                    _foodList.emit(result.data)
                }
                else -> {
                    Log.e(TAG, "식품 인기 상품 목록 조회 실패")
                }
            }
        }
    }
}