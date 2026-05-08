package com.iosglass.components.feedback

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterialSpec
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Alert — iOS UIAlertController.alert style
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class AlertAction(
    val label: String,
    val style: AlertActionStyle = AlertActionStyle.Default,
    val onClick: () -> Unit = {},
)

enum class AlertActionStyle { Default, Destructive, Cancel }

@Composable
fun IOSAlert(
    visible: Boolean,
    title: String,
    message: String? = null,
    actions: List<AlertAction>,
    onDismiss: (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(IOSAnimation.iosTween(200)) + scaleIn(initialScale = 0.85f, animationSpec = IOSAnimation.snappySpring()),
        exit = fadeOut(IOSAnimation.iosTween(150)) + scaleOut(targetScale = 0.85f, animationSpec = IOSAnimation.iosTween(150)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.32f))
                .clickable(interactionSource = remember { }, indication = null) { onDismiss?.invoke() },
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .width(270.dp)
                    .glassEffect(GlassMaterials.elevated, RoundedCornerShape(14.dp))
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = typography.headline,
                    color = colors.label,
                    textAlign = TextAlign.Center,
                )
                message?.let {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = typography.subheadline,
                        color = colors.labelSecondary,
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(Modifier.height(16.dp))

                // Buttons
                if (actions.size == 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(0.5.dp),
                    ) {
                        actions.forEach { action ->
                            AlertButton(action = action, modifier = Modifier.weight(1f))
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(0.5.dp),
                    ) {
                        actions.forEach { action ->
                            AlertButton(action = action, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertButton(action: AlertAction, modifier: Modifier) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    val fg = when (action.style) {
        AlertActionStyle.Destructive -> colors.red
        else -> colors.blue
    }
    val weight = if (action.style == AlertActionStyle.Cancel) FontWeight.SemiBold else FontWeight.Normal

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colors.fillQuaternary)
            .clickable { action.onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = action.label, style = typography.body.copy(fontWeight = weight), color = fg)
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Action Sheet — iOS UIAlertController.actionSheet style
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSActionSheet(
    visible: Boolean,
    title: String? = null,
    message: String? = null,
    actions: List<AlertAction>,
    onCancel: (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(IOSAnimation.iosTween(250)),
        exit = fadeOut(IOSAnimation.iosTween(200)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.32f))
                .clickable(interactionSource = remember { }, indication = null) { onCancel?.invoke() },
            contentAlignment = Alignment.BottomCenter,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Actions group
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .glassEffect(GlassMaterials.elevated, RoundedCornerShape(14.dp)),
                ) {
                    title?.let {
                        Text(
                            text = it,
                            style = typography.subheadline,
                            color = colors.labelSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                        )
                    }
                    message?.let {
                        Text(
                            text = it,
                            style = typography.footnote,
                            color = colors.labelTertiary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                        )
                    }
                    actions.forEachIndexed { index, action ->
                        if (index > 0) {
                            Box(Modifier.fillMaxWidth().height(0.5.dp).background(colors.separator))
                        }
                        ActionSheetButton(action)
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Cancel
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .glassEffect(GlassMaterials.elevated, RoundedCornerShape(14.dp))
                        .clickable { onCancel?.invoke() }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Cancel",
                        style = typography.body.copy(fontWeight = FontWeight.SemiBold),
                        color = colors.blue,
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionSheetButton(action: AlertAction) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    val fg = if (action.style == AlertActionStyle.Destructive) colors.red else colors.blue
    val weight = if (action.style == AlertActionStyle.Cancel) FontWeight.SemiBold else FontWeight.Normal

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(57.dp)
            .clickable { action.onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = action.label, style = typography.body.copy(fontWeight = weight), color = fg)
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Toast / HUD — brief notification
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
enum class ToastStyle { Success, Error, Info }

@Composable
fun IOSToast(
    visible: Boolean,
    message: String,
    style: ToastStyle = ToastStyle.Info,
    icon: String? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    val bgColor = when (style) {
        ToastStyle.Success -> colors.green
        ToastStyle.Error -> colors.red
        ToastStyle.Info -> colors.label.copy(alpha = 0.85f)
    }
    val defaultIcon = when (style) {
        ToastStyle.Success -> "✓"
        ToastStyle.Error -> "✕"
        ToastStyle.Info -> "ℹ"
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 60.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(bgColor.copy(alpha = 0.92f))
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = icon ?: defaultIcon, fontSize = 16.sp, color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = message,
                    style = typography.subheadline.copy(fontWeight = FontWeight.Medium),
                    color = Color.White,
                )
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// ProgressView — circular + linear
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSProgressView(
    modifier: Modifier = Modifier,
    progress: Float? = null,  // null = indeterminate
    color: Color? = null,
    trackColor: Color? = null,
) {
    val colors = IOSTheme.colors
    val activeColor = color ?: colors.blue
    val bgColor = trackColor ?: colors.fillTertiary

    if (progress != null) {
        // Determinate
        val fraction = progress.coerceIn(0f, 1f)
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(bgColor),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(activeColor),
            )
        }
    } else {
        // Indeterminate — simple animated bar
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(bgColor),
        ) {
            // Animated segment
            val infiniteTransition = androidx.compose.animation.core.InfiniteTransition
            val animVal = androidx.compose.animation.core.rememberInfiniteTransition(label = "progress")
            val offset by animVal.animateFloat(
                initialValue = -0.3f,
                targetValue = 1f,
                animationSpec = androidx.compose.animation.core.infiniteRepeatable(
                    animation = androidx.compose.animation.core.tween(1200, easing = IOSAnimation.EaseInOut),
                    repeatMode = androidx.compose.animation.core.RepeatMode.Restart,
                ),
                label = "progress_offset",
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(4.dp)
                    .offset(x = with(androidx.compose.ui.platform.LocalDensity.current) { (offset * 300).toDp() })
                    .clip(RoundedCornerShape(2.dp))
                    .background(activeColor),
            )
        }
    }
}

@Composable
fun IOSCircularProgressView(
    modifier: Modifier = Modifier,
    color: Color? = null,
    strokeWidth: androidx.compose.ui.unit.Dp = 3.dp,
) {
    val colors = IOSTheme.colors
    val activeColor = color ?: colors.blue

    androidx.compose.foundation.Canvas(
        modifier = modifier.size(24.dp),
    ) {
        val stroke = strokeWidth.toPx()
        val radius = (size.minDimension - stroke) / 2
        val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)

        drawCircle(
            color = colors.fillTertiary,
            radius = radius,
            center = center,
            style = androidx.compose.ui.graphics.drawscope.Stroke(stroke),
        )

        // Spinning arc
        drawArc(
            color = activeColor,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(stroke, cap = androidx.compose.ui.graphics.StrokeCap.Round),
        )
    }
}

// Helpers
@Composable
private fun remember() = androidx.compose.runtime.remember

private fun Modifier.offset(x: androidx.compose.ui.unit.Dp) =
    this.then(androidx.compose.foundation.layout.offset(x))

private fun Modifier.size(dp: androidx.compose.ui.unit.Dp) =
    this.then(androidx.compose.foundation.layout.size(dp))
