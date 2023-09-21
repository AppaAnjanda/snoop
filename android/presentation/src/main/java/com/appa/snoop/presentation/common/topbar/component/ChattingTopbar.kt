package com.appa.snoop.presentation.common.topbar.component

import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.topbar.utils.ActionMenuItem
import com.appa.snoop.presentation.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object ChattingTopbar: Topbars {
    override val isCenterTopBar: Boolean = true
    override val route: String = Router.CATEGORY_CHATTING_ROUTER_NAME
    override val isAppBarVisible: Boolean = true
    override val navigationIcon: Int = R.drawable.ic_back
    override val onNavigationIconClick: () -> Unit = {
        _buttons.tryEmit(AppBarIcons.NavigationIcon)
    }
    override val navigationIconContentDescription: String? = null
    override val title: String = Router.Title.CATEGORY_CHATTING
    override val actions: List<ActionMenuItem> =
        listOf(

        )

    private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
    val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

    enum class AppBarIcons {
        NavigationIcon,
//        ChatIcon,
//        SearchIcon
    }
}