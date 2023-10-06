package com.appa.snoop.presentation.common.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.appa.snoop.presentation.ui.theme.PrimaryColor

@Composable
fun ClickableButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    buttonColor: Color = PrimaryColor,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val bgColor = if (isPressed) buttonColor.copy(
        red = buttonColor.red * 0.96f,
        blue = buttonColor.blue * 0.96f,
        green = buttonColor.green * 0.96f
    ) else buttonColor

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(bgColor),
        interactionSource = interactionSource,
        elevation = elevation,
        enabled = enabled
    ) {
        content()
    }
}