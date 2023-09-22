package com.appa.snoop.presentation.common.topbar.utils

import com.appa.snoop.presentation.common.topbar.component.LoginTopBar
import com.appa.snoop.presentation.common.topbar.component.NotificationTopbar
import com.appa.snoop.presentation.common.topbar.component.CategoryTopbar
import com.appa.snoop.presentation.common.topbar.component.ChattingTopbar
import com.appa.snoop.presentation.common.topbar.component.MainTopbar
import com.appa.snoop.presentation.common.topbar.component.ModifyProfileTopbar
import com.appa.snoop.presentation.common.topbar.component.ProductTopbar
import com.appa.snoop.presentation.common.topbar.component.SearchTopbar
import com.appa.snoop.presentation.common.topbar.component.SignupTopBar
import com.appa.snoop.presentation.common.topbar.component.Topbars
import com.appa.snoop.presentation.navigation.Router
import retrofit2.http.HEAD

fun getTopbar(route: String?): Topbars? = when (route) {
    Router.MAIN_HOME_ROUTER_NAME,
    Router.MAIN_LIKE_ITEM_ROUTER_NAME,
    Router.MAIN_MY_PAGE_ROUTER_NAME -> MainTopbar

    Router.MAIN_CATEGORY_ROUTER_NAME -> CategoryTopbar
    Router.CATEGORY_PRODUCT_ROUTER_NAME -> ProductTopbar
    // 추가할 화면 추가구성 가능 아래에다 쭈루룩 (탑 앱바 다를 시만, 공통된 부분은 최대한 묶읍시다 위처럼)
    Router.MAIN_SEARCH_ROUTER_NAME -> SearchTopbar
    Router.MAIN_NOTIFICATION_ROUTER_NAME -> NotificationTopbar
    Router.MY_PAGE_MODIFY_PROFILE_ROUTER_NAME -> ModifyProfileTopbar

    Router.MAIN_LOGIN_ROUTER_NAME -> LoginTopBar

    Router.LOGIN_SIGNUP_ROUTER_NAME -> SignupTopBar

    Router.CATEGORY_CHATTING_ROUTER_NAME -> ChattingTopbar

    else -> null
}