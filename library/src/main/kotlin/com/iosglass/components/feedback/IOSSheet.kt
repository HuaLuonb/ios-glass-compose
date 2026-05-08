package com.iosglass.components.feedback

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.iosglass.core.GlassMaterialSpec
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Sheet — iOS half/full sheet with glass
// ══════════════════════════════════════════════════════════════════════════════

enum class SheetPresentation { Half, Full, Medium }

@Immutable
data class SheetConfig(
    val presentation: SheetPresentation = SheetPresentation.Half,
    val showDragIndicator: Boolean = true,
    val dismissible: Boolean = true,
)

@Composable
fun IOSSheet(
    visible: Boolean,
    config: SheetConfig = SheetConfig(),
    onDismiss: () -> Unit = {},
    title: String? = null,
    content: @Composable () -> Unit,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.32f))
                .clickable(interactionSource = remember { }, indication = null) {
                    if (config.dismissible) onDismiss()
                },
            contentAlignment = Alignment.BottomCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        when (config.presentation) {
                            SheetPresentation.Full -> Modifier.fillMaxSize()
                            SheetPresentation.Half -> Modifier.fillMaxWidth(0.95f)
                            SheetPresentation.Medium -> Modifier.fillMaxWidth(0.95f)
                        }
                    )
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .glassEffect(GlassMaterials.elevated, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .pointerInput(onDismiss) {
                        detectVerticalDragGestures { _, dragAmount ->
                            if (dragAmount > 50 && config.dismissible) onDismiss()
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Drag indicator
                if (config.showDragIndicator) {
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(2.5.dp))
                            .background(colors.fill),
                    )
                    Spacer(Modifier.height(8.dp))
                }

                // Title
                title?.let {
                    Text(
                        text = it,
                        style = typography.headline,
                        color = colors.label,
                    )
                    Spacer(Modifier.height(12.dp))
                }

                // Content
                Box(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    content()
                }
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Dynamic Island
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class IslandContent(
    val title: String,
    val subtitle: String? = null,
    val leading: String? = null,
    val trailing: String? = null,
    val accentColor: Color? = null,
    val progress: Float? = null,
)

@Composable
fun DynamicIsland(
    content: IslandContent,
    expanded: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    val height by animateFloatAsState(
        targetValue = if (expanded) 88f else 36f,
        animationSpec = IOSAnimation.snappySpring(),
        label = "island_h",
    )
    val widthFraction by animateFloatAsState(
        targetValue = if (expanded) 0.92f else 0.42f,
        animationSpec = IOSAnimation.snappySpring(),
        label = "island_w",
    )
    val cornerRadius = height / 2

    Box(
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .height(height.dp)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(Color.Black)
            .border(0.5.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(cornerRadius.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        if (expanded) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                content.leading?.let {
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(content.accentColor ?: colors.blue),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = it, fontSize = 22.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = content.title,
                        style = typography.subheadline.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White,
                        maxLines = 1,
                    )
                    content.subtitle?.let {
                        Text(
                            text = it,
                            style = typography.caption1,
                            color = Color.White.copy(alpha = 0.55f),
                            maxLines = 1,
                        )
                    }
                    content.progress?.let {
                        Spacer(Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .clip(RoundedCornerShape(1.5.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(it)
                                    .height(3.dp)
                                    .clip(RoundedCornerShape(1.5.dp))
                                    .background(Color.White),
                            )
                        }
                    }
                }

                content.trailing?.let {
                    Spacer(Modifier.width(8.dp))
                    Text(text = it, fontSize = 26.sp)
                }
            }
        } else {
            // Compact
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                content.leading?.let {
                    Text(text = it, fontSize = 14.sp)
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    text = content.title,
                    style = typography.caption1.copy(fontWeight = FontWeight.Medium),
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 1,
                )
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Context Menu (long-press popup)
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class ContextMenuItem(
    val label: String,
    val icon: String? = null,
    val destructive: Boolean = false,
    val onClick: () -> Unit,
)

@Composable
fun IOSContextMenu(
    visible: Boolean,
    items: List<ContextMenuItem>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
                .clickable(interactionSource = remember { }, indication = null) { onDismiss() },
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = modifier
                    .width(240.dp)
                    .glassEffect(GlassMaterials.elevated, RoundedCornerShape(14.dp))
                    .padding(vertical = 6.dp),
            ) {
                items.forEachIndexed { index, item ->
                    if (index > 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .padding(horizontal = 12.dp)
                                .background(colors.separator),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                item.onClick()
                                onDismiss()
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = item.label,
                            style = typography.body,
                            color = if (item.destructive) colors.red else colors.label,
                            modifier = Modifier.weight(1f),
                        )
                        item.icon?.let {
                            Text(text = it, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

// Helpers
@Composable
private fun remember() = androidx.compose.runtime.remember

private fun Modifier.graphicsLayer(block: androidx.compose.ui.graphics.GraphicsLayerScope.() -> Unit) =
    this.then(Modifier.graphicsLayer(block))
