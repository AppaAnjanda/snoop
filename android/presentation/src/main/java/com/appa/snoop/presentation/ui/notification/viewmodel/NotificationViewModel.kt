package com.appa.snoop.presentation.ui.notification.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appa.snoop.domain.model.NetworkResult
import com.appa.snoop.domain.model.notification.Notification
import com.appa.snoop.domain.usecase.notification.DeleteAllNotificationHistory
import com.appa.snoop.domain.usecase.notification.DeleteNotificationHistory
import com.appa.snoop.domain.usecase.notification.GetNotificationHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "[김진영] NotificationViewModel"

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationHistory: GetNotificationHistory,
    private val deleteNotificationHistory: DeleteNotificationHistory,
    private val deleteAllNotificationHistory: DeleteAllNotificationHistory
) : ViewModel() {

    private val _notificationListState = MutableStateFlow(emptyList<Notification>())
    val notificationListState: StateFlow<List<Notification>> = _notificationListState.asStateFlow()

    private val _deletedNotificationState = MutableStateFlow("")
    val deletedNotificationState: StateFlow<String> = _deletedNotificationState.asStateFlow()

    fun getNotificationHistory() {
        viewModelScope.launch {
            val result = getNotificationHistory.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    _notificationListState.emit(result.data)
                    Log.d(TAG, "getNotificationHistory: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "getNotificationHistory: 알림 리스트 조회 실패")
                }
            }
        }
    }

    fun deleteNotificationHistory(alertId: Int) {
        viewModelScope.launch {
            val result = deleteNotificationHistory.invoke(alertId = alertId)

            when (result) {
                is NetworkResult.Success -> {
                    _deletedNotificationState.emit(result.data)
                    Log.d(TAG, "deleteNotificationHistory: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "deleteNotificationHistory: 알림 삭제 실패")
                }
            }
        }
    }

    fun deleteAllNotificationHistory() {
        viewModelScope.launch {
            val result = deleteAllNotificationHistory.invoke()

            when (result) {
                is NetworkResult.Success -> {
                    _deletedNotificationState.emit(result.data)
                    Log.d(TAG, "deleteAllNotificationHistory: ${result.data}")
                }

                else -> {
                    Log.d(TAG, "deleteAllNotificationHistory: 알림 전체 삭제 실패")
                }
            }
        }
    }

    private val _notificationState = MutableStateFlow(emptyList<Notification>())
    val notificationState: StateFlow<List<Notification>> = _notificationState.asStateFlow()

    fun removeItem(currentItem: Notification) {
        _notificationState.update {
            val mutableList = it.toMutableList()
            mutableList.remove(currentItem)
            mutableList
        }
    }

}
