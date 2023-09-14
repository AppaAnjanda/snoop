package com.appa.snoop.presentation.common.topbar.utils

import com.appa.snoop.presentation.common.topbar.component.BackTopbar
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.common.topbar.component.MainTopbar
import com.appa.snoop.presentation.common.topbar.component.ProductTopbar
import com.appa.snoop.presentation.common.topbar.component.SearchTopbar
import com.appa.snoop.presentation.common.topbar.component.Topbars
import com.appa.snoop.presentation.navigation.Router

fun getTopbar(route: String?): Topbars? = when (route) {
    Router.MAIN_HOME_ROUTER_NAME,
    Router.MAIN_LIKE_ITEM_ROUTER_NAME,
    Router.MAIN_MY_PAGE_ROUTER_NAME -> MainTopbar

    Router.MAIN_CATEGORY_ROUTER_NAME -> CategoryTopbar
    Router.CATEGORY_PRODUCT_ROUTER_NAME -> ProductTopbar
    // 추가할 화면 추가구성 가능 아래에다 쭈루룩 (탑 앱바 다를 시만, 공통된 부분은 최대한 묶읍시다 위처럼)
    Router.MAIN_SEARCH_ROUTER_NAME -> SearchTopbar

    Router.MAIN_NOTIFICATION_ROUTER_NAME -> BackTopbar

    else -> null
}