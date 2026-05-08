package com.iosglass.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.core.GlassMaterialSpec
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Navigation Stack — simplified
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class NavigationRoute(
    val title: String,
    val content: @Composable () -> Unit,
)

// ══════════════════════════════════════════════════════════════════════════════
// Grid Layout
// ══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IOSGrid(
    columns: Int = 2,
    modifier: Modifier = Modifier,
    horizontalSpacing: Int = 12,
    verticalSpacing: Int = 12,
    content: @Composable () -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing.dp),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing.dp),
        maxItemsInEachRow = columns,
        content = { content() },
    )
}

// ══════════════════════════════════════════════════════════════════════════════
// Feature Card Grid — iOS App Library style
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class FeatureItem(
    val title: String,
    val icon: String,
    val accentColor: Color,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun IOSFeatureGrid(
    items: List<FeatureItem>,
    modifier: Modifier = Modifier,
    columns: Int = 4,
) {
    val typography = IOSTheme.typography
    val colors = IOSTheme.colors

    Column(modifier = modifier.fillMaxWidth()) {
        items.chunked(columns).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                row.forEach { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(enabled = item.onClick != null) { item.onClick?.invoke() }
                            .padding(vertical = 8.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(item.accentColor),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = item.icon, fontSize = 28.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = item.title,
                            style = typography.caption2,
                            color = colors.label,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(68.dp),
                        )
                    }
                }
                // Fill remaining slots
                repeat(columns - row.size) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Toolbar
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSToolbar(
    modifier: Modifier = Modifier,
    leading: @Composable (() -> Unit)? = null,
    center: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .glassEffect(GlassMaterials.thin)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) { leading?.invoke() }
        center?.invoke()
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) { trailing?.invoke() }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Empty State
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSEmptyState(
    icon: String,
    title: String,
    message: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = icon, fontSize = 48.sp)
        Spacer(Modifier.height(12.dp))
        Text(
            text = title,
            style = typography.title3,
            color = colors.label,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        message?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                text = it,
                style = typography.subheadline,
                color = colors.labelSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
        }
        actionLabel?.let {
            Spacer(Modifier.height(16.dp))
            com.iosglass.components.controls.IOSButton(
                label = it,
                onClick = { onAction?.invoke() },
                style = com.iosglass.components.controls.IOSButtonStyle.Filled,
            )
        }
    }
}
