package com.appa.snoop.presentation.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavController
import com.appa.snoop.presentation.common.topbar.utils.rememberAppBarState
import com.appa.snoop.presentation.ui.signup.component.DuckieTextField
import com.appa.snoop.presentation.ui.signup.component.SignupTextField
import com.appa.snoop.presentation.ui.theme.BackgroundColor
import com.appa.snoop.presentation.ui.theme.WhiteColor
import com.appa.snoop.presentation.util.effects.SignupLaunchedEffect
import ir.kaaveh.sdpcompose.sdp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavController
) {
    val appBarState = rememberAppBarState(navController = navController)

    SignupLaunchedEffect(navController = navController)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteColor),
//        topBar = { SharedTopAppBar(appBarState = appBarState) }

    ) { it
        var text by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.sdp)
        ) {
//            Factorial()
            SignupTextField()
            DuckieTextField(
                text,
                onTextChanged = {
                    text = it
                }
            )
            Spacer(modifier = Modifier.height(10.sdp))

            Text(text = "test")
            Text(text = "test")
            Text(text = "test")
            Text(text = "test")
            Text(text = "test")
            Text(text = "test")
            Text(text = "test")
            Text(text = "test")
        }
    }
}