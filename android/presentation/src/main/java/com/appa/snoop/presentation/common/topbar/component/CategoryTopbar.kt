package com.appa.snoop.presentation.common.topbar.component

import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.topbar.component.MainTopbar.MainRoute
import com.appa.snoop.presentation.common.topbar.utils.ActionMenuItem
import com.appa.snoop.presentation.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object CategoryTopbar: Topbars {

    override val isCenterTopBar: Boolean = true
    override val route: String = Router.MAIN_CATEGORY_ROUTER_NAME
    override val isAppBarVisible: Boolean = true
    override val navigationIcon: Int = R.drawable.ic_menu
    override val onNavigationIconClick: () -> Unit = {
        _buttons.tryEmit(AppBarIcons.MenuIcon)
    }
    override val navigationIconContentDescription: String? = null
    override val title: String = Router.Title.MAIN_CATEGORY

    override val actions: List<ActionMenuItem> =
        listOf(
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Alarm",
                onClick = {
                    _buttons.tryEmit(AppBarIcons.ChatIcon)
                },
                icon = R.drawable.ic_chat,
                contentDescription = "채팅",
            ),
        )

    private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
    val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

    enum class AppBarIcons {
        MenuIcon,
        ChatIcon,
    }
}