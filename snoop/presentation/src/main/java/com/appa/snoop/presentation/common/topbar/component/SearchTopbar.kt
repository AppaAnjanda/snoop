package com.appa.snoop.presentation.common.topbar.component

import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.topbar.utils.ActionMenuItem
import com.appa.snoop.presentation.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SearchTopbar : Topbars {

    override val isCenterTopBar: Boolean = true
    override val route: String = Router.MAIN_SEARCH_ROUTER_NAME
    override val isAppBarVisible: Boolean = true
    override val navigationIcon: Int = R.drawable.ic_back
    override val onNavigationIconClick: () -> Unit = {
        _buttons.tryEmit(AppBarIcons.NavigationIcon)
    }
    override val navigationIconContentDescription: String? = "뒤로가기"
    override val title: String = Router.Title.MAIN_SEARCH

    override val actions: List<ActionMenuItem> =
        listOf(
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Alarm",
                onClick = {
                    _buttons.tryEmit(AppBarIcons.Alarm)
                },
                icon = R.drawable.ic_alarm,
                contentDescription = null,
            ),
        )

    private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
    val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

    enum class AppBarIcons {
        NavigationIcon,
        Alarm,
    }
}