package com.appa.snoop.presentation.ui.chatting.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.chat.ChatItem
import com.appa.snoop.domain.usecase.chatting.GetPreChattingListUseCase
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "[김희웅] ChatPagingDataSource"
class ChatPagingDataSource @Inject constructor(
    private val getPreChattingListUseCase: GetPreChattingListUseCase,
    private val roomId: Int
): PagingSource<Int, ChatItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatItem> {
        val page = params.key ?: 1
        return try {
            val result = getPreChattingListUseCase.invoke(roomId, page)

            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "load: 토탈 페이지? ${result.data.totalPage}")
                    Log.d(TAG, "load: 리스트 목록 -> ${result.data.contents}")
                    LoadResult.Page(
                        data = result.data.contents,
                        prevKey = null,
                        nextKey = if (page < result.data.totalPage) page + 1 else null
                    )
                }
                else -> {
                    Log.d(TAG, "load: 페이징 클래스 내부 통신 오류")
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

    override fun getRefreshKey(state: PagingState<Int, ChatItem>): Int? {
        return state.anchorPosition
    }
}