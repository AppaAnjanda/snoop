package com.appa.snoop.presentation.ui.mypage.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.appa.snoop.presentation.ui.mypage.profile.component.ModifyProfileImg
import com.appa.snoop.presentation.ui.theme.WhiteColor

@Composable
fun ModifyProfileScreen(navController: NavHostController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var nickName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(WhiteColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModifyProfileImg(imageUri = imageUri, onImageChange = { uri -> imageUri = uri })
    }

}