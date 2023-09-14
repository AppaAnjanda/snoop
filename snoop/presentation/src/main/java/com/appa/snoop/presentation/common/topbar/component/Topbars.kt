package com.appa.snoop.presentation.common.topbar.component

import androidx.annotation.DrawableRes
import com.appa.snoop.presentation.common.topbar.utils.ActionMenuItem

sealed interface Topbars {
    val isCenterTopBar: Boolean
    val route: String
    val isAppBarVisible: Boolean
    @get:DrawableRes
    val navigationIcon: Int?
    val navigationIconContentDescription: String?
    val onNavigationIconClick: (() -> Unit)?
    val title: Any
    val actions: List<ActionMenuItem>
}