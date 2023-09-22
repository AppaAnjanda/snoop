package com.appa.snoop.presentation.util.extensions

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.emptyFlow

class NoRippleInteractionSource(override val interactions: kotlinx.coroutines.flow.Flow<Interaction> = emptyFlow()) :
    MutableInteractionSource {


    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true


}