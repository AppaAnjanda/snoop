package com.appa.snoop.presentation.common.topbar.component

import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.common.topbar.utils.ActionMenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object MainTopbar : Topbars {
    val MainRoute = "main"

    override val isCenterTopBar: Boolean = false
    override val route: String = MainRoute
    override val isAppBarVisible: Boolean = true
    override val navigationIcon: Int? = null
    override val onNavigationIconClick: () -> Unit = {
        _buttons.tryEmit(AppBarIcons.NavigationIcon)
    }
    override val navigationIconContentDescription: String? = null
    override val title: Int = R.drawable.img_logo

    override val actions: List<ActionMenuItem> =
        listOf(
//            ActionMenuItem.IconMenuItem.AlwaysShown(
//                title = "Search",
//                onClick = {
//                    _buttons.tryEmit(AppBarIcons.Search)
//                },
//                icon = R.drawable.ic_search,
//                contentDescription = null,
//            ),
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
//        Search,
        Alarm,
    }
}