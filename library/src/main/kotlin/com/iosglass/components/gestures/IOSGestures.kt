package com.iosglass.components.gestures

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Swipe Action Row
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class SwipeAction(
    val label: String,
    val icon: String? = null,
    val color: Color,
    val onClick: () -> Unit,
)

@Composable
fun IOSSwipeActionRow(
    modifier: Modifier = Modifier,
    leadingActions: List<SwipeAction> = emptyList(),
    trailingActions: List<SwipeAction> = emptyList(),
    content: @Composable () -> Unit,
) {
    val colors = IOSTheme.colors

    // Simplified swipe — full swipe gesture requires nested scroll
    // This shows the row with action buttons visible
    var showLeading by remember { mutableStateOf(false) }
    var showTrailing by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        if (showLeading && leadingActions.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
            ) {
                leadingActions.forEach { action ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .background(action.color)
                            .clickable {
                                action.onClick()
                                showLeading = false
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            action.icon?.let { Text(it, fontSize = 16.sp, color = Color.White) }
                            Spacer(Modifier.width(4.dp))
                            Text(
                                action.label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }

        content()

        if (showTrailing && trailingActions.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
            ) {
                trailingActions.forEach { action ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .background(action.color)
                            .clickable {
                                action.onClick()
                                showTrailing = false
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            action.icon?.let { Text(it, fontSize = 16.sp, color = Color.White) }
                            Spacer(Modifier.width(4.dp))
                            Text(
                                action.label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Pull to Refresh
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = IOSTheme.colors

    Column(modifier = modifier.fillMaxWidth()) {
        if (isRefreshing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                contentAlignment = Alignment.Center,
            ) {
                com.iosglass.components.feedback.IOSCircularProgressView(
                    color = colors.labelTertiary,
                )
            }
        }
        content()
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Haptic Feedback Helper
// ══════════════════════════════════════════════════════════════════════════════

object IOSHaptics {
    enum class Style { Light, Medium, Heavy, Soft, Rigid, Success, Warning, Error }

    @Composable
    fun trigger(style: Style = Style.Light) {
        val context = androidx.compose.ui.platform.LocalContext.current
        val vibrator = remember {
            context.getSystemService(android.content.Context.VIBRATOR_SERVICE) as? android.os.Vibrator
        }

        // Use Android vibration API
        // In production, use HapticFeedbackConstants
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Glass Shimmer Effect — loading placeholder
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun GlassShimmer(
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val transition = androidx.compose.animation.core.rememberInfiniteTransition(label = "shimmer")
    val offset by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1200, easing = IOSAnimation.EaseInOut),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart,
        ),
        label = "shimmer_offset",
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colors.fillQuaternary,
                        colors.fillTertiary.copy(alpha = 0.5f),
                        colors.fillQuaternary,
                    ),
                    start = Offset(offset * 300f, 0f),
                    end = Offset(offset * 300f + 300f, 0f),
                )
            ),
    )
}

// Helpers
@Composable
private fun remember() = androidx.compose.runtime.remember

private fun Modifier.graphicsLayer(block: androidx.compose.ui.graphics.GraphicsLayerScope.() -> Unit) =
    this.then(Modifier.graphicsLayer(block))
