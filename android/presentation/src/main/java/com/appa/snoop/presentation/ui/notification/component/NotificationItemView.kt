package com.appa.snoop.presentation.ui.notification.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.domain.model.notification.Notification
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.util.DateUtil
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationItemComponent(item: Notification, onClickedItem: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .padding(
                    start = 16.sdp,
                    end = 16.sdp,
                    top = 10.sdp,
                    bottom = 10.sdp
                )
                .noRippleClickable {
                    onClickedItem()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "로고",
                        modifier = Modifier.size(20.sdp)
                    )
                    Spacer(modifier = Modifier.size(4.sdp))
                    Text(
                        text = item.title,
                        style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.ssp)
                    )
                }
                Spacer(modifier = Modifier.size(4.sdp))
                Text(
                    text = item.body,
                    lineHeight = 18.ssp,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.ssp,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = DateUtil.dateToString(item.createTime),
                    lineHeight = 18.ssp,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.ssp,
                        color = Color.Gray
                    )
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .build(),
                contentDescription = "상품 사진",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Inside
            )
        }
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
    }


}
