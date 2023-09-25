package com.appa.snoop.presentation.ui.category.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.category.Product
import com.appa.snoop.domain.usecase.category.GetProductListByCategoryUseCase
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "[김희웅] ProductPagingDataSource"
class ProductPagingDataSource @Inject constructor(
    private val categoryUseCase: GetProductListByCategoryUseCase,
    private val majorName: String,
    private val minorName: String
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 1
        Log.d(TAG, "load: 페이징 성공 페이지? ${page}")
        return try {
            val result = categoryUseCase.invoke(majorName = majorName, minorName = minorName, page)

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
                    LoadResult.Invalid()
                }
            }
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        Log.d(TAG, "getRefreshKey: ${state}")
        return state.anchorPosition
    }
}
