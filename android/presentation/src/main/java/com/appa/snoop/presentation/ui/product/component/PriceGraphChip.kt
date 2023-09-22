package com.appa.snoop.presentation.ui.product.component

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appa.snoop.presentation.ui.theme.PrimaryColor
import com.appa.snoop.presentation.util.extensions.NoRippleInteractionSource
import kotlinx.coroutines.flow.emptyFlow
import java.util.concurrent.Flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChipsSelectable(
    chips: List<String>,
    initStartPosition: Int = 0,
    onChipClick: (index: Int) -> Unit
) {
    var selected by rememberSaveable { mutableStateOf(initStartPosition) }

    Row(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        chips.forEachIndexed { index, item ->
            FilterChip(
                selected = selected == index,
                onClick = {
                    selected = index
                    onChipClick(index)
                },
                interactionSource = NoRippleInteractionSource(),
                label = {
                    Text(
                        item,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PrimaryColor,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.LightGray,
                    borderWidth = 0.5.dp
                )
            )
        }
    }

}