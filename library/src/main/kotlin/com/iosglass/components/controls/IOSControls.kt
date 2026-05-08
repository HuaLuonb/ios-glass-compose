package com.iosglass.components.controls

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Toggle — iOS switch
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val colors = IOSTheme.colors

    val trackColor by animateColorAsState(
        targetValue = if (checked) colors.green else colors.fillTertiary,
        animationSpec = IOSAnimation.iosTween(150),
        label = "toggle_track",
    )

    val thumbOffset by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = IOSAnimation.bouncySpring(),
        label = "toggle_thumb",
    )

    val trackWidth = 51.dp
    val trackHeight = 31.dp
    val thumbSize = 27.dp
    val thumbTravel = trackWidth - thumbSize - 4.dp

    Box(
        modifier = modifier
            .width(trackWidth)
            .height(trackHeight)
            .clip(RoundedCornerShape(trackHeight / 2))
            .background(trackColor)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .offset(x = thumbTravel * thumbOffset)
                .size(thumbSize)
                .shadow(3.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.12f))
                .clip(CircleShape)
                .background(Color.White)
                .border(0.5.dp, Color.Black.copy(alpha = 0.04f), CircleShape),
        )
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Slider — iOS style with draggable thumb
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    enabled: Boolean = true,
    activeColor: Color? = null,
) {
    val colors = IOSTheme.colors
    val density = LocalDensity.current
    val activeTint = activeColor ?: colors.blue
    val trackHeight = 4.dp
    val thumbSize = 28.dp

    var trackWidthPx by remember { mutableFloatStateOf(0f) }
    val fraction = ((value - valueRange.start) / (valueRange.endInclusive - valueRange.start))
        .coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize)
            .onSizeChanged { trackWidthPx = it.width.toFloat() - with(density) { thumbSize.toPx() } },
    ) {
        // Background track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(trackHeight / 2))
                .background(colors.fillTertiary),
        )

        // Active track
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .height(trackHeight)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(trackHeight / 2))
                .background(activeTint),
        )

        // Thumb
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = with(density) { (fraction * trackWidthPx).toDp() })
                .size(thumbSize)
                .shadow(6.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.15f))
                .clip(CircleShape)
                .background(Color.White)
                .border(0.5.dp, Color.Black.copy(alpha = 0.08f), CircleShape)
                .pointerInput(enabled, valueRange) {
                    if (!enabled) return@pointerInput
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        val delta = dragAmount / trackWidthPx * (valueRange.endInclusive - valueRange.start)
                        onValueChange((value + delta).coerceIn(valueRange))
                    }
                },
        )
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Stepper — iOS +/- control
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSStepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    range: IntRange = 0..10,
    step: Int = 1,
    enabled: Boolean = true,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Row(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colors.fillQuaternary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Minus
        Box(
            modifier = Modifier
                .size(36.dp, 32.dp)
                .clickable(enabled = enabled && value > range.first) {
                    onValueChange((value - step).coerceAtLeast(range.first))
                },
            contentAlignment = Alignment.Center,
        ) {
            Text("−", fontSize = 20.sp, color = if (enabled) colors.blue else colors.labelQuaternary)
        }

        // Divider
        Box(
            modifier = Modifier
                .width(0.5.dp)
                .height(20.dp)
                .background(colors.separator),
        )

        // Value
        Box(
            modifier = Modifier.width(44.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "$value",
                style = typography.body,
                color = colors.label,
            )
        }

        // Divider
        Box(
            modifier = Modifier
                .width(0.5.dp)
                .height(20.dp)
                .background(colors.separator),
        )

        // Plus
        Box(
            modifier = Modifier
                .size(36.dp, 32.dp)
                .clickable(enabled = enabled && value < range.last) {
                    onValueChange((value + step).coerceAtMost(range.last))
                },
            contentAlignment = Alignment.Center,
        ) {
            Text("+", fontSize = 20.sp, color = if (enabled) colors.blue else colors.labelQuaternary)
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Button — iOS styles
// ══════════════════════════════════════════════════════════════════════════════

enum class IOSButtonStyle { Filled, Gray, Plain, Borderless, Glass }

@Composable
fun IOSButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: IOSButtonStyle = IOSButtonStyle.Filled,
    enabled: Boolean = true,
    icon: String? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    val (bg, fg) = when (style) {
        IOSButtonStyle.Filled -> colors.blue to Color.White
        IOSButtonStyle.Gray -> colors.fill to colors.blue
        IOSButtonStyle.Plain -> Color.Transparent to colors.blue
        IOSButtonStyle.Borderless -> Color.Transparent to colors.blue
        IOSButtonStyle.Glass -> Color.Transparent to colors.label
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = IOSAnimation.iosTween(80),
        label = "btn_alpha",
    )

    Box(
        modifier = modifier
            .height(if (style == IOSButtonStyle.Filled || style == IOSButtonStyle.Gray) 50.dp else 44.dp)
            .graphicsLayer { this.alpha = alpha }
            .then(
                if (style == IOSButtonStyle.Glass) Modifier.glassEffect(GlassMaterials.regular, RoundedCornerShape(14.dp))
                else Modifier.clip(RoundedCornerShape(14.dp))
            )
            .background(bg)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
            ) { onClick() }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Text(text = icon, fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = typography.body.copy(fontWeight = FontWeight.SemiBold),
                color = if (enabled) fg else fg.copy(alpha = 0.4f),
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Segmented Control
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSSegmentedControl(
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colors.fillTertiary)
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        items.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            val bg by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color.Transparent,
                animationSpec = IOSAnimation.iosTween(200),
                label = "seg_bg",
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) colors.label else colors.labelSecondary,
                animationSpec = IOSAnimation.iosTween(200),
                label = "seg_fg",
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(28.dp)
                    .shadow(if (isSelected) 1.dp else 0.dp, RoundedCornerShape(7.dp))
                    .clip(RoundedCornerShape(7.dp))
                    .background(bg)
                    .clickable { onSelected(index) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = typography.footnote.copy(fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal),
                    color = textColor,
                )
            }
        }
    }
}

// Helpers
@Composable
private fun remember() = androidx.compose.runtime.remember

private fun MutableInteractionSource.Companion.remember() =
    androidx.compose.runtime.remember { MutableInteractionSource() }

private fun Modifier.graphicsLayer(block: androidx.compose.ui.graphics.GraphicsLayerScope.() -> Unit) =
    this.then(Modifier.graphicsLayer(block))

private fun Modifier.offset(x: Dp) =
    this.then(androidx.compose.foundation.layout.offset(x))

private fun Modifier.height(dp: Dp) =
    this.then(androidx.compose.foundation.layout.height(dp))
