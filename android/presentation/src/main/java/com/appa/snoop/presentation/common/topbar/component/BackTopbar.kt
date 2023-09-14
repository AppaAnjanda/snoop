package com.appa.snoop.presentation.common.topbar.component

import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.topbar.utils.ActionMenuItem
import com.appa.snoop.presentation.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object BackTopbar : Topbars {
    override val isCenterTopBar: Boolean = true
    override val route: String = Router.MAIN_NOTIFICATION_ROUTER_NAME
    override val isAppBarVisible: Boolean = true
    override val navigationIcon: Int = R.drawable.ic_back
    override val navigationIconContentDescription: String? = "뒤로 가기"
    override val onNavigationIconClick: () -> Unit = {
        _buttons.tryEmit(AppBarIcons.NavigationIcon)
    }
    override val title: String = Router.Title.MAIN_NOTIFICATION
    override val actions: List<ActionMenuItem> = listOf()

    private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
    val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

    enum class AppBarIcons {
        NavigationIcon
    }
}