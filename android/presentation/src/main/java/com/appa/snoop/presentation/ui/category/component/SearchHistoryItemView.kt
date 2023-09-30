package com.appa.snoop.presentation.ui.category.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.category.CategoryViewModel
import com.appa.snoop.presentation.ui.theme.BlackColor
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun SearchHistoryItem(
    name: String,
    categoryViewModel: CategoryViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.sdp, horizontal = 4.sdp),
        horizontalArrangement = Arrangement.spacedBy(4.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(14.sdp),
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = null,
            tint = BlackColor
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .noRippleClickable {
                    categoryViewModel.setTextSearch(name)
                },
            text = name,
            fontSize = 14.ssp,
        )
        Icon(
            modifier = Modifier
                .size(10.sdp)
                .clickable {
                    categoryViewModel.deleteHistory(name)
                    categoryViewModel.delete()
                },
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "삭제 버튼",
            tint = BlackColor
        )
        Spacer(modifier = Modifier.width(4.sdp))
    }
}