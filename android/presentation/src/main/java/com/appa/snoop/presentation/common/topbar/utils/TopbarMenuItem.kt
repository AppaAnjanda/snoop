package com.appa.snoop.presentation.common.topbar.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.R
import ir.kaaveh.sdpcompose.sdp

sealed interface ActionMenuItem {
    val title: String
    // 백 버튼 클릭시
    val onClick: () -> Unit

    sealed interface IconMenuItem : ActionMenuItem {
        @get:DrawableRes
        val icon: Int
        val contentDescription: String?

        data class AlwaysShown(
            override val title: String,
            override val contentDescription: String?,
            override val onClick: () -> Unit,
            override val icon: Int,
        ) : IconMenuItem
    }
}

private data class MenuItems(
    val alwaysShownItems: List<ActionMenuItem.IconMenuItem>
)

private fun splitMenuItems(
    items: List<ActionMenuItem>
): MenuItems {
    val alwaysShownItems: MutableList<ActionMenuItem.IconMenuItem> =
        items.filterIsInstance<ActionMenuItem.IconMenuItem.AlwaysShown>().toMutableList()

    return MenuItems(
        alwaysShownItems = alwaysShownItems
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsMenu(
    items: List<ActionMenuItem>,
    notificationCount: Int = 0
) {
    val menuItems = remember(
        key1 = items,
    ) {
        splitMenuItems(items)
    }

    menuItems.alwaysShownItems.forEach { item ->
        when (item.icon) {
            // 알람 아이콘이면? 뱃지가 보여야 함
            R.drawable.ic_alarm -> {
                Box(modifier = Modifier.wrapContentSize()) {
                    IconButton(onClick = item.onClick) {
                        Image(
                            modifier = Modifier
                                .wrapContentHeight()
                                .width(18.sdp),
                            painter = painterResource(id = R.drawable.ic_alarm), // 이미지 리소스
                            contentDescription = null, // 이미지 설명 (접근성을 위해 필요)
                        )
                    }
                    // 뱃지에 대한 색상
                    if (notificationCount > 0) {
                        Badge(
                            modifier = Modifier.align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 8.dp)
                        ) {
                            if (notificationCount > 9) {
                                Text(text = "9+")
                            } else {
                                Text(text = notificationCount.toString())
                            }
                        }
                    }
                }
            }
            // 아니면 그냥 보여주기
            else -> {
                IconButton(onClick = item.onClick) {
                    Icon(
                        modifier = Modifier
                            .size(16.sdp),
                        painter = painterResource(id = item.icon),
                        contentDescription = item.contentDescription,
                    )
                }
            }
        }
    }
}