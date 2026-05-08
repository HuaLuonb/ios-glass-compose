package com.iosglass.components.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Search Bar — iOS style
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    onSearch: ((String) -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colors.fillQuaternary)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "🔍", fontSize = 14.sp, color = colors.labelTertiary)
            Spacer(Modifier.width(6.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = typography.body.copy(color = colors.label),
                singleLine = true,
                cursorBrush = SolidColor(colors.blue),
                decorationBox = { inner ->
                    Box {
                        if (query.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = typography.body,
                                color = colors.labelTertiary,
                            )
                        }
                        inner()
                    }
                },
            )

            if (query.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(colors.fillTertiary)
                        .clickable { onQueryChange("") },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "✕", fontSize = 10.sp, color = colors.labelSecondary)
                }
            }
        }

        // Cancel button
        if (isFocused || query.isNotEmpty()) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Cancel",
                style = typography.body,
                color = colors.blue,
                modifier = Modifier.clickable {
                    onQueryChange("")
                    onCancel?.invoke()
                },
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Chip / Tag
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSChip(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    icon: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    val bg = if (selected) colors.blue.copy(alpha = 0.12f) else colors.fillQuaternary
    val fg = if (selected) colors.blue else colors.labelSecondary

    Row(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        icon?.let {
            Text(text = it, fontSize = 14.sp)
            Spacer(Modifier.width(4.dp))
        }
        Text(
            text = label,
            style = typography.subheadline.copy(fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal),
            color = fg,
        )
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Badge
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSBadge(
    count: Int,
    modifier: Modifier = Modifier,
    color: Color? = null,
) {
    val colors = IOSTheme.colors
    val bg = color ?: colors.red
    val text = if (count > 99) "99+" else "$count"

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            lineHeight = 13.sp,
        )
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Link — tappable text
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Text(
        text = text,
        style = typography.body,
        color = colors.blue,
        modifier = modifier.clickable { onClick() },
    )
}

// ══════════════════════════════════════════════════════════════════════════════
// Label — icon + text
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSLabel(
    title: String,
    icon: String,
    modifier: Modifier = Modifier,
    iconTint: Color? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(iconTint ?: colors.blue),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = icon, fontSize = 14.sp, color = Color.White)
        }
        Spacer(Modifier.width(10.dp))
        Text(
            text = title,
            style = typography.body,
            color = colors.label,
        )
    }
}
