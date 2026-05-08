package com.iosglass.core

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.iosglass.theme.IOSTheme

/**
 * iOS 26 Background System
 *
 * Wallpapers, gradients, and ambient backgrounds
 */
object IOSBackgrounds {

    /** Default iOS light background with radial gradient */
    @androidx.compose.runtime.Composable
    fun Modifier.iosBackground(): Modifier {
        val colors = IOSTheme.colors
        return this.drawBehind {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colors.bgPrimary,
                        colors.bgSecondary,
                    ),
                    center = Offset(size.width / 2, size.height / 3),
                    radius = size.width * 0.9f,
                )
            )
        }
    }

    /** Warm gradient */
    @androidx.compose.runtime.Composable
    fun Modifier.warmBackground(): Modifier {
        return this.drawBehind {
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFF1E6),
                        Color(0xFFFCE4EC),
                        Color(0xFFE8EAF6),
                    ),
                )
            )
        }
    }

    /** Cool gradient */
    @androidx.compose.runtime.Composable
    fun Modifier.coolBackground(): Modifier {
        return this.drawBehind {
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD),
                        Color(0xFFE8EAF6),
                        Color(0xFFF3E5F5),
                    ),
                )
            )
        }
    }

    /** Dark gradient */
    @androidx.compose.runtime.Composable
    fun Modifier.darkBackground(): Modifier {
        return this.drawBehind {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F0F1A),
                    ),
                    center = Offset(size.width / 2, size.height / 3),
                    radius = size.width * 0.8f,
                )
            )
        }
    }
}

/**
 * Liquid transition effect between screens
 */
fun Modifier.liquidTransition(progress: Float): Modifier {
    return this.drawBehind {
        val scale = 1f - progress * 0.05f
        val alpha = 1f - progress * 0.3f
        drawRect(
            color = Color.Black.copy(alpha = progress * 0.2f),
        )
    }
}
