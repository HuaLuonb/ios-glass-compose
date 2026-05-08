package com.iosglass.components.media

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iosglass.animations.IOSAnimation
import com.iosglass.core.GlassMaterialSpec
import com.iosglass.core.GlassMaterials
import com.iosglass.core.glassEffect
import com.iosglass.theme.IOSTheme
import kotlin.math.absoluteValue

// ══════════════════════════════════════════════════════════════════════════════
// Page Dots
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSPageDots(
    totalDots: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    activeColor: Color? = null,
    inactiveColor: Color? = null,
) {
    val colors = IOSTheme.colors
    val active = activeColor ?: colors.label
    val inactive = inactiveColor ?: colors.fill

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(totalDots) { index ->
            val isActive = index == currentIndex
            val size by animateFloatAsState(
                targetValue = if (isActive) 8f else 6f,
                animationSpec = IOSAnimation.bouncySpring(),
                label = "dot_size",
            )
            Box(
                modifier = Modifier
                    .size(size.dp)
                    .clip(CircleShape)
                    .background(if (isActive) active else inactive),
            )
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Carousel / PageView
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSPageView(
    pageCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable (pageIndex: Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            content(page)
        }

        Spacer(Modifier.height(8.dp))
        IOSPageDots(
            totalDots = pageCount,
            currentIndex = pagerState.currentPage,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Card Carousel — horizontal scrolling cards
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class CarouselItem(
    val title: String,
    val subtitle: String? = null,
    val icon: String? = null,
    val accentColor: Color? = null,
    val gradient: List<Color>? = null,
)

@Composable
fun IOSCardCarousel(
    items: List<CarouselItem>,
    modifier: Modifier = Modifier,
    onItemClicked: ((Int) -> Unit)? = null,
) {
    val colors = IOSTheme.colors
    val typography = IOSTheme.typography

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
    ) {
        items(items.size) { index ->
            val item = items[index]
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            item.gradient ?: listOf(
                                item.accentColor ?: colors.blue,
                                (item.accentColor ?: colors.blue).copy(alpha = 0.7f),
                            )
                        )
                    )
                    .clickable { onItemClicked?.invoke(index) }
                    .padding(14.dp),
            ) {
                Column {
                    item.icon?.let {
                        Text(text = it, fontSize = 24.sp)
                        Spacer(Modifier.height(8.dp))
                    }
                    Text(
                        text = item.title,
                        style = typography.subheadline.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    item.subtitle?.let {
                        Text(
                            text = it,
                            style = typography.caption1,
                            color = Color.White.copy(alpha = 0.7f),
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// Hero Card — large feature card
// ══════════════════════════════════════════════════════════════════════════════

@Composable
fun IOSHeroCard(
    title: String,
    subtitle: String,
    icon: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val typography = IOSTheme.typography

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.linearGradient(gradient))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart),
        ) {
            Text(text = icon, fontSize = 40.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                style = typography.title1.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
            )
            Text(
                text = subtitle,
                style = typography.body,
                color = Color.White.copy(alpha = 0.8f),
            )
        }
    }
}

// Helpers
private fun Modifier.graphicsLayer(block: androidx.compose.ui.graphics.GraphicsLayerScope.() -> Unit) =
    this.then(Modifier.graphicsLayer(block))
