package com.appa.snoop.presentation.ui.signup

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.appa.snoop.presentation.util.effects.SignupLaunchedEffect

@Composable
fun SignupScreen(
    navController: NavController
) {
    SignupLaunchedEffect(navController = navController)
}