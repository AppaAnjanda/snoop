package com.appa.snoop.presentation.ui.category.utils

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.usecase.category.GetProductListByKeywordUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "[김희웅] ProductKeywordPagingDat"
class ProductKeywordPagingDataSource @Inject constructor(
    private val categoryUseCase: GetProductListByKeywordUseCase,
    private val keyoword: String,
    private val minPrice: Int,
    private val maxPrice: Int
//    private val onFail: () -> Unit
) : PagingSource<Int, Product>() {
    val snackBarHostState = SnackbarHostState()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 1
        Log.d(TAG, "load: 페이징 성공 페이지? ${page}")
        Log.d(TAG, "load: 최대 최소 가격 설정? min -> ${minPrice}, max -> ${maxPrice}")
        return try {
            val result = categoryUseCase.invoke(keyoword, page, minPrice, maxPrice)

            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "load: 토탈 페이지? ${result.data.totalPage}")
                    Log.d(TAG, "load: 리스트 목록 -> ${result.data.contents}")
                    LoadResult.Page(
                        data = result.data.contents,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page < result.data.totalPage) page + 1 else null
                    )
                }
                else -> {
                    Log.d(TAG, "load: 페이징 클래스 내부 통신 오류")
                    snackBarHostState.showSnackbar("검색 결과가 없습니다.")
//                    onFail()
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
            }
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }
}
