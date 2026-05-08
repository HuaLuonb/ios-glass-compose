package com.iosglass.components.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iosglass.animations.IOSAnimation
import com.iosglass.animations.iosPressScale
import com.iosglass.core.GlassMaterialSpec
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Glass Card — the core container
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    spec: GlassMaterialSpec = GlassMaterials.regular,
    shape: Shape = RoundedCornerShape(20.dp),
    pressable: Boolean = false,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier = modifier
            .then(
                if (pressable) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick ?: {},
                    )
                } else Modifier
            )
            .iosPressScale(pressed = isPressed && pressable)
            .then(
                if (spec.isElevated) {
                    Modifier.shadow(
                        elevation = spec.elevation.coerceAtLeast(8.dp),
                        shape = shape,
                        ambientColor = Color.Black.copy(alpha = 0.08f),
                        spotColor = Color.Black.copy(alpha = 0.12f),
                    )
                } else Modifier
            )
            .glassEffect(spec, shape)
            .padding(contentPadding),
        content = content,
    )
}

// ══════════════════════════════════════════════════════════════════════════════
// Plain Card — no glass, just rounded + shadow
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = IOSTheme.colors
    Column(
        modifier = modifier
            .shadow(2.dp, shape, ambientColor = Color.Black.copy(alpha = 0.04f))
            .clip(shape)
            .background(colors.bgSecondary)
            .padding(contentPadding),
        content = content,
    )
}

// ══════════════════════════════════════════════════════════════════════════════
// Section Header — grouped list section title
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    androidx.compose.foundation.layout.Box(
        modifier = modifier.padding(horizontal = 32.dp, vertical = 6.dp),
    ) {
        androidx.compose.material3.Text(
            text = title.uppercase(),
            style = typography.footnote,
            color = colors.labelSecondary,
        )
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Separator
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun Separator(
    modifier: Modifier = Modifier,
    inset: Dp = 0.dp,
) {
    val colors = IOSTheme.colors
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = inset)
            .height(0.5.dp)
            .background(colors.separator),
    )
}

private fun Modifier.height(dp: Dp) = this.then(androidx.compose.foundation.layout.height(dp))
