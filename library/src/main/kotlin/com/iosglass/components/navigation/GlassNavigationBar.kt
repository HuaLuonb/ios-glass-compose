package com.iosglass.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Navigation Bar — iOS style with glass
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class NavigationBarConfig(
    val title: String = "",
    val subtitle: String? = null,
    val showBackButton: Boolean = false,
    val largeTitle: Boolean = false,
    val transparent: Boolean = false,
)

@Composable
fun GlassNavigationBar(
    config: NavigationBarConfig,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Column(modifier = modifier.fillMaxWidth()) {
        // Inline nav bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(44.dp)
                .then(
                    if (!config.transparent) Modifier.glassEffect(GlassMaterials.thick)
                    else Modifier
                )
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Leading area
            if (config.showBackButton) {
                Row(
                    modifier = Modifier
                        .clickable { onBack?.invoke() }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "‹",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Light,
                        color = colors.blue,
                        lineHeight = 28.sp,
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text = "Back",
                        style = typography.body,
                        color = colors.blue,
                    )
                }
            } else {
                leading?.invoke()
            }

            Spacer(Modifier.weight(1f))

            // Center title
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = config.title,
                    style = typography.headline,
                    color = colors.label,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                config.subtitle?.let {
                    Text(
                        text = it,
                        style = typography.caption1,
                        color = colors.labelSecondary,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // Trailing
            Box(
                contentAlignment = Alignment.CenterEnd,
            ) {
                trailing?.invoke()
            }
        }

        // Large title
        if (config.largeTitle) {
            Text(
                text = config.title,
                style = typography.largeTitle,
                color = colors.label,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp),
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Tab Bar
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class TabItem(
    val label: String,
    val icon: String,
    val selectedIcon: String = icon,
    val badge: Int? = null,
    val badgeText: String? = null,
)

@Composable
fun GlassTabBar(
    items: List<TabItem>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(83.dp)
            .glassEffect(GlassMaterials.thick)
            .padding(horizontal = 4.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.12f else 1f,
                animationSpec = com.iosglass.animations.IOSAnimation.bouncySpring(),
                label = "tab_scale",
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                        indication = null,
                    ) { onSelected(index) }
                    .padding(top = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = if (isSelected) item.selectedIcon else item.icon,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale; scaleY = scale
                            },
                    )
                    // Badge
                    val badgeCount = item.badge
                    val badgeStr = item.badgeText ?: if (badgeCount != null && badgeCount > 0) {
                        if (badgeCount > 99) "99+" else "$badgeCount"
                    } else null

                    if (badgeStr != null) {
                        Box(
                            modifier = Modifier
                                .offset(x = 8.dp, y = (-2).dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(colors.red)
                                .padding(horizontal = 5.dp, vertical = 1.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = badgeStr,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                lineHeight = 12.sp,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(2.dp))
                Text(
                    text = item.label,
                    style = typography.caption2.copy(
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    ),
                    color = if (isSelected) colors.blue else colors.labelTertiary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }
        }
    }
}

// Helpers
@Composable
private fun remember() = androidx.compose.runtime.remember

private fun Modifier.graphicsLayer(block: androidx.compose.ui.graphics.GraphicsLayerScope.() -> Unit) =
    this.then(Modifier.graphicsLayer(block))

private fun Modifier.offset(x: Dp, y: Dp) =
    this.then(androidx.compose.foundation.layout.offset(x, y))

private fun Modifier.height(dp: Dp) =
    this.then(androidx.compose.foundation.layout.height(dp))
