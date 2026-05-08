package com.iosglass.components.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme

// ══════════════════════════════════════════════════════════════════════════════
// Picker — iOS wheel-style picker
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSPicker(
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleCount: Int = 5,
    itemHeight: Dp = 44.dp,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)
    val totalHeight = itemHeight * visibleCount

    // Detect center item
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                val centerIndex = index + if (offset > itemHeight.value * 50 / 100) 1 else 0
                if (centerIndex in items.indices && centerIndex != selectedIndex) {
                    onSelected(centerIndex)
                }
            }
    }

    Box(
        modifier = modifier
            .height(totalHeight)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        // Selection highlight
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.fillQuaternary),
        )

        // Gradient fade top/bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(colors.bgPrimary, Color.Transparent)
                    )
                ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, colors.bgPrimary)
                    )
                ),
        )

        // Wheel list
        LazyColumn(
            state = listState,
            modifier = Modifier.height(totalHeight),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
        ) {
            // Spacer items for centering
            item { Spacer(Modifier.height(itemHeight * 2)) }
            items(items.size) { index ->
                val isSelected = index == selectedIndex
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = items[index],
                        style = typography.body.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = if (isSelected) 20.sp else 17.sp,
                        ),
                        color = if (isSelected) colors.label else colors.labelTertiary,
                    )
                }
            }
            item { Spacer(Modifier.height(itemHeight * 2)) }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Date Picker — wheel-style
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class IOSDate(
    val year: Int = 2026,
    val month: Int = 1,     // 1-12
    val day: Int = 1,       // 1-31
    val hour: Int = 0,      // 0-23
    val minute: Int = 0,    // 0-59
)

@Composable
fun IOSDatePicker(
    date: IOSDate,
    onDateChange: (IOSDate) -> Unit,
    modifier: Modifier = Modifier,
    showTime: Boolean = false,
    minuteInterval: Int = 1,
) {
    val colors = IOSTheme.colors
    val months = listOf("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

    val daysInMonth = when (date.month) {
        2 -> if (date.year % 4 == 0 && (date.year % 100 != 0 || date.year % 400 == 0)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }

    val days = (1..daysInMonth).map { "$it" }
    val years = (1900..2100).map { "$it" }
    val hours = (0..23).map { if (it < 10) "0$it" else "$it" }
    val minutes = (0 until 60 step minuteInterval).map { if (it < 10) "0$it" else "$it" }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Month
        IOSPicker(
            items = months,
            selectedIndex = (date.month - 1).coerceIn(0, 11),
            onSelected = { onDateChange(date.copy(month = it + 1)) },
            modifier = Modifier.weight(2f),
            visibleCount = 3,
            itemHeight = 36.dp,
        )

        // Day
        IOSPicker(
            items = days,
            selectedIndex = (date.day - 1).coerceIn(0, days.lastIndex),
            onSelected = { onDateChange(date.copy(day = it + 1)) },
            modifier = Modifier.weight(1f),
            visibleCount = 3,
            itemHeight = 36.dp,
        )

        // Year
        IOSPicker(
            items = years,
            selectedIndex = (date.year - 1900).coerceIn(0, years.lastIndex),
            onSelected = { onDateChange(date.copy(year = 1900 + it)) },
            modifier = Modifier.weight(1.5f),
            visibleCount = 3,
            itemHeight = 36.dp,
        )

        if (showTime) {
            // Hour
            IOSPicker(
                items = hours,
                selectedIndex = date.hour.coerceIn(0, 23),
                onSelected = { onDateChange(date.copy(hour = it)) },
                modifier = Modifier.weight(1f),
                visibleCount = 3,
                itemHeight = 36.dp,
            )

            Text(":", fontWeight = FontWeight.Bold, color = colors.label)

            // Minute
            IOSPicker(
                items = minutes,
                selectedIndex = (date.minute / minuteInterval).coerceIn(0, minutes.lastIndex),
                onSelected = { onDateChange(date.copy(minute = it * minuteInterval)) },
                modifier = Modifier.weight(1f),
                visibleCount = 3,
                itemHeight = 36.dp,
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Color Picker — grid of iOS system colors
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    colors: List<Color> = defaultPickerColors(),
) {
    val themeColors = IOSTheme.colors

    Column(modifier = modifier.fillMaxWidth()) {
        // Grid
        val chunked = colors.chunked(6)
        chunked.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                row.forEach { color ->
                    val isSelected = color == selectedColor
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color)
                            .clickable { onColorSelected(color) }
                            .then(
                                if (isSelected) Modifier.drawBehind {
                                    drawCircle(
                                        color = Color.White,
                                        radius = size.minDimension / 2 + 4.dp.toPx(),
                                    )
                                    drawCircle(
                                        color = color,
                                        radius = size.minDimension / 2,
                                    )
                                } else Modifier
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun defaultPickerColors(): List<Color> {
    val c = IOSTheme.colors
    return listOf(
        c.red, c.orange, c.yellow, c.green, c.mint, c.teal,
        c.cyan, c.blue, c.indigo, c.purple, c.pink, c.brown,
        Color(0xFF8E8E93), Color(0xFF636366), Color(0xFF48484A),
        Color(0xFF3A3A3C), Color(0xFF2C2C2E), Color(0xFF1C1C1E),
    )
}

private fun Modifier.size(dp: Dp) = this.then(androidx.compose.foundation.layout.size(dp))
