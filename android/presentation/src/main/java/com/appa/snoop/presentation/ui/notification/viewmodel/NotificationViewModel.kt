package com.appa.snoop.presentation.ui.notification.viewmodel

import androidx.lifecycle.ViewModel
import com.appa.snoop.presentation.ui.notification.component.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificationViewModel : ViewModel() {

    private val _notificationState = MutableStateFlow(emptyList<Notification>())
    val notificationState: StateFlow<List<Notification>> = _notificationState.asStateFlow()

    init {
        // Initialize the list of email messages
        // when the ViewModel is created
        _notificationState.update { sampleNotifications() }
    }

    fun refresh() {
        _notificationState.update {
            sampleNotifications()
        }
    }

    fun removeItem(currentItem: Notification) {
        _notificationState.update {
            val mutableList = it.toMutableList()
            mutableList.remove(currentItem)
            mutableList
        }
    }

    private fun sampleNotifications() = listOf(
        Notification(1),
        Notification(2),
        Notification(3),
        Notification(4),
        Notification(5)
    )
}
