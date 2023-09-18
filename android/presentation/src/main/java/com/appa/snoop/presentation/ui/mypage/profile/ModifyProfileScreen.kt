package com.appa.snoop.presentation.ui.mypage.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.appa.snoop.presentation.ui.mypage.profile.component.ModifyProfilNickname
import com.appa.snoop.presentation.ui.mypage.profile.component.ModifyProfileImg
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.ModifyProfileLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ModifyProfileScreen(navController: NavHostController) {
    ModifyProfileLaunchedEffect(navController)
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var nickName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(WhiteColor)
            .padding(start = 20.sdp, end = 20.sdp, top = 40.sdp, bottom = 40.sdp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModifyProfileImg(imageUri = imageUri, onImageChange = { uri -> imageUri = uri })
        Spacer(modifier = Modifier.size(16.sdp))
        ModifyProfilNickname { value -> nickName = value }
    }

}