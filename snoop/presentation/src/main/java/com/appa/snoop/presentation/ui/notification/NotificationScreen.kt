package com.appa.snoop.presentation.ui.notification

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appa.snoop.presentation.ui.notification.component.NotificationView

@Composable
fun NotificationScreen(navController: NavController) {
    NotificationView()
}

@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = rememberNavController())
}