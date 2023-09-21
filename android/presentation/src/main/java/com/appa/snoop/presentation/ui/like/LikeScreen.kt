package com.appa.snoop.presentation.ui.like

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appa.snoop.presentation.ui.like.component.SelelctComponent
import com.appa.snoop.presentation.ui.theme.BackgroundColor2
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.MainLaunchedEffect
import com.appa.snoop.presentation.util.extensions.noRippleClickable
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LikeScreen(
    navController: NavController
) {
    MainLaunchedEffect(navController)

    // 전체 아이템의 체크 상태를 저장하는 리스트
    val numberOfItems = 10
    var checkedStates by remember { mutableStateOf(List(numberOfItems) { false }) }

    // '모두 선택' 체크박스의 상태
    var allSelected by remember { mutableStateOf(false) }

    // '모두 선택' 체크박스 상태 변경
    fun toggleSelectAll() {
        allSelected = !allSelected
        checkedStates = List(numberOfItems) { allSelected }
    }

    // 개별 체크박스 상태 변경
    fun toggleIndividualSelection(index: Int) {
        checkedStates = checkedStates.toMutableList().also {
            it[index] = !it[index]
        }
        allSelected = checkedStates.all { it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor)
    ) {
        SelelctComponent(allSelected,
            onChangeCheckedState = { toggleSelectAll() }
        )
        LazyColumn {
            item {
                HorizontalDivider(thickness = 6.sdp, color = BackgroundColor2)
            }
            itemsIndexed(checkedStates) { index, isChecked ->
                LikeItem(isChecked, onCheckedChange = { toggleIndividualSelection(index) },
                    onClick = { /* TODO delete */ })
                HorizontalDivider(color = BackgroundColor2)
            }
        }
    }
}

@Composable
fun LikeItem(value: Boolean, onCheckedChange: (Any) -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.sdp, end = 16.sdp, top = 12.sdp, bottom = 12.sdp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.sdp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Checkbox(
                checked = value,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.size(16.sdp),
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Gray
                )
            )
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = "삭제",
                modifier = Modifier.noRippleClickable { onClick() })

        }
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(80.sdp)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://media.istockphoto.com/id/1358386001/photo/apple-macbook-pro.jpg?s=612x612&w=0&k=20&c=d14HA-i0EHpdvNvccdJQ5pAkQt8bahxjjb6fO6hs4E8=")
                    .build(),
                contentDescription = "제품 이미지",
                contentScale = ContentScale.FillWidth
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 4.sdp, end = 4.sdp, bottom = 16.sdp)
            ) {
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = "Apple 맥북 프로 14 스페이스 그레이 M2 pro 10코어",
                    style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
                )
                Spacer(modifier = Modifier.size(8.sdp))
                Text(
                    text = "2,620,000원",
                    style = TextStyle(fontSize = 14.ssp, fontWeight = FontWeight.Medium),
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "현재 지정 가격",
                style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
            )
            Spacer(modifier = Modifier.size(8.sdp))

            Text(
                text = "2,420,000",
                style = TextStyle(fontSize = 12.ssp, fontWeight = FontWeight.Normal),
            )
        }

    }
}



@Composable
fun EditPrice(price: Int, onPriceChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var nickName by remember { mutableStateOf(price.toString()) }
    TextField(
        value = nickName,
        onValueChange = { nickName = it },
        textStyle = TextStyle(
//            fontSize = 8.ssp,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onSearch = {
            onPriceChange(nickName)
            keyboardController?.hide()
        }),
        singleLine = true,

        modifier = Modifier.requiredHeight(32.sdp),
//            .clip(RoundedCornerShape(12.sdp))
//            .border(1.sdp, Color.LightGray, RoundedCornerShape(12.sdp)),
        shape = ShapeDefaults.Medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun LikeScreenPreview() {
    LikeScreen(navController = rememberNavController())
}