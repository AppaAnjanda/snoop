package com.appa.snoop.presentation.ui.mypage.modifyprofile.component

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.appa.snoop.presentation.R
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ModifyProfileImg(
    img: String?,
    imageUri: Uri?,
    granted: Boolean,
    showGranted: () -> Unit,
    onImageChange: (Uri) -> Unit,
) {
    val context = LocalContext.current
    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null)
                onImageChange(uri)
        }
    // API level 28 이하는 MediaStore.Images.Media.getBitmap 사용 (deprecated)
    // 그 이상부터 ImageDecoder.createSource 사용
    val bitmap = imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }

    Box {
        if (imageUri == null || bitmap == null) {
            AsyncImage(
                model = img,
                contentDescription = "profile image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
            )
        } else {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(160.dp)
                    .padding(8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.sdp, bottom = 8.sdp)
                .clickable {
                    if (granted) {
                        getContent.launch("image/*")
                    } else {
                        showGranted()
                    }
                },
            color = PrimaryColor,
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "사진 불러오기",
                modifier = Modifier
                    .size(24.sdp)
                    .padding(4.sdp)
            )
        }
    }
}