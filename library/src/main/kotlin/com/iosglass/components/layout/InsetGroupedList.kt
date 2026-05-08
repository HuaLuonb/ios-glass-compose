package com.iosglass.components.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterialSpec
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Inset Grouped List — iOS Settings style
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class ListItem(
    val title: String,
    val subtitle: String? = null,
    val value: String? = null,
    val icon: String? = null,
    val iconTint: Color? = null,
    val showChevron: Boolean = false,
    val destructive: Boolean = false,
    val onClick: (() -> Unit)? = null,
    val trailing: (@Composable () -> Unit)? = null,
)

@Immutable
data class ListSection(
    val title: String? = null,
    val footer: String? = null,
    val items: List<ListItem>,
)

@Composable
fun InsetGroupedList(
    sections: List<ListSection>,
    modifier: Modifier = Modifier,
    glassSpec: GlassMaterialSpec? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        sections.forEach { section ->
            // Section header
            section.title?.let {
                Text(
                    text = it.uppercase(),
                    style = typography.footnote,
                    color = colors.labelSecondary,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 4.dp),
                )
            }

            // Items container
            val shape = RoundedCornerShape(12.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .then(
                        if (glassSpec != null) Modifier.glassEffect(glassSpec, shape)
                        else Modifier.clip(shape).background(colors.bgSecondary)
                    ),
            ) {
                section.items.forEachIndexed { index, item ->
                    if (index > 0) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .padding(start = if (item.icon != null) 56.dp else 16.dp)
                                .background(colors.separator),
                        )
                    }
                    ListItemRow(item)
                }
            }

            // Footer
            section.footer?.let {
                Text(
                    text = it,
                    style = typography.footnote,
                    color = colors.labelSecondary,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 2.dp),
                )
            }
        }
    }
}

@Composable
private fun ListItemRow(item: ListItem) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = item.onClick != null) { item.onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Icon
        if (item.icon != null) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(item.iconTint ?: colors.blue),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = item.icon, fontSize = 16.sp, color = Color.White)
            }
            Spacer(Modifier.width(12.dp))
        }

        // Title + subtitle
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = typography.body,
                color = if (item.destructive) colors.red else colors.label,
            )
            item.subtitle?.let {
                Text(
                    text = it,
                    style = typography.subheadline,
                    color = colors.labelSecondary,
                )
            }
        }

        // Value
        item.value?.let {
            Text(
                text = it,
                style = typography.body,
                color = colors.labelSecondary,
            )
            Spacer(Modifier.width(4.dp))
        }

        // Trailing composable
        item.trailing?.invoke()

        // Chevron
        if (item.showChevron) {
            Spacer(Modifier.width(4.dp))
            Text(
                text = "›",
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
                color = colors.labelQuaternary,
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Disclosure Group — expandable section
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun DisclosureGroup(
    title: String,
    modifier: Modifier = Modifier,
    initiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = IOSAnimation.iosTween(200),
        label = "disclosure_rotate",
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = typography.body,
                color = colors.label,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "›",
                fontSize = 18.sp,
                color = colors.labelTertiary,
                modifier = Modifier.graphicsLayer { rotationZ = rotation },
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp),
                content = content,
            )
        }
    }
}

// Helpers
private fun Modifier.graphicsLayer(block: androidx.compose.ui.graphics.GraphicsLayerScope.() -> Unit) =
    this.then(Modifier.graphicsLayer(block))
