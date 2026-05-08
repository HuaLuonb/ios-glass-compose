package com.iosglass.core

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ══════════════════════════════════════════════════════════════════════════════
// iOS 26 Color System — full HIG palette
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class IOSColors(
    // System colors
    val blue: Color = Color(0xFF007AFF),
    val green: Color = Color(0xFF34C759),
    val indigo: Color = Color(0xFF5856D6),
    val orange: Color = Color(0xFFFF9500),
    val pink: Color = Color(0xFFFF2D55),
    val purple: Color = Color(0xFFAF52DE),
    val red: Color = Color(0xFFFF3B30),
    val teal: Color = Color(0xFF5AC8FA),
    val yellow: Color = Color(0xFFFFCC00),
    val mint: Color = Color(0xFF00C7BE),
    val cyan: Color = Color(0xFF32ADE6),
    val brown: Color = Color(0xFFA2845E),

    // Backgrounds
    val bgPrimary: Color = Color(0xFFF2F2F7),
    val bgSecondary: Color = Color(0xFFFFFFFF),
    val bgTertiary: Color = Color(0xFFF2F2F7),
    val bgGrouped: Color = Color(0xFFF2F2F7),
    val bgElevated: Color = Color(0xFFFFFFFF),

    // Glass
    val glassTint: Color = Color.White.copy(alpha = 0.12f),
    val glassBorder: Color = Color.White.copy(alpha = 0.25f),

    // Text
    val label: Color = Color(0xFF000000),
    val labelSecondary: Color = Color(0xFF3C3C43).copy(alpha = 0.6f),
    val labelTertiary: Color = Color(0xFF3C3C43).copy(alpha = 0.3f),
    val labelQuaternary: Color = Color(0xFF3C3C43).copy(alpha = 0.18f),

    // Separators
    val separator: Color = Color(0xFF545458).copy(alpha = 0.65f),
    val separatorOpaque: Color = Color(0xFFC6C6C8),

    // Fill
    val fill: Color = Color(0xFF787880).copy(alpha = 0.2f),
    val fillSecondary: Color = Color(0xFF787880).copy(alpha = 0.16f),
    val fillTertiary: Color = Color(0xFF767680).copy(alpha = 0.12f),
    val fillQuaternary: Color = Color(0xFF747480).copy(alpha = 0.08f),

    // Grouped background
    val groupedBgPrimary: Color = Color(0xFFF2F2F7),
    val groupedBgSecondary: Color = Color(0xFFFFFFFF),
    val groupedBgTertiary: Color = Color(0xFFF2F2F7),
)

val LightColors = IOSColors()

val DarkColors = IOSColors(
    blue = Color(0xFF0A84FF),
    green = Color(0xFF30D158),
    indigo = Color(0xFF5E5CE6),
    orange = Color(0xFFFF9F0A),
    pink = Color(0xFFFF375F),
    purple = Color(0xFFBF5AF2),
    red = Color(0xFFFF453A),
    teal = Color(0xFF64D2FF),
    yellow = Color(0xFFFFD60A),
    mint = Color(0xFF66D4CF),
    cyan = Color(0xFF55C4E0),
    brown = Color(0xFFAC8E68),
    bgPrimary = Color(0xFF000000),
    bgSecondary = Color(0xFF1C1C1E),
    bgTertiary = Color(0xFF2C2C2E),
    bgGrouped = Color(0xFF000000),
    bgElevated = Color(0xFF2C2C2E),
    glassTint = Color.White.copy(alpha = 0.06f),
    glassBorder = Color.White.copy(alpha = 0.12f),
    label = Color(0xFFFFFFFF),
    labelSecondary = Color(0xFFEBEBF5).copy(alpha = 0.6f),
    labelTertiary = Color(0xFFEBEBF5).copy(alpha = 0.3f),
    labelQuaternary = Color(0xFFEBEBF5).copy(alpha = 0.18f),
    separator = Color(0xFF545458).copy(alpha = 0.65f),
    separatorOpaque = Color(0xFF38383A),
    fill = Color(0xFF787880).copy(alpha = 0.36f),
    fillSecondary = Color(0xFF787880).copy(alpha = 0.32f),
    fillTertiary = Color(0xFF767680).copy(alpha = 0.24f),
    fillQuaternary = Color(0xFF747480).copy(alpha = 0.18f),
    groupedBgPrimary = Color(0xFF000000),
    groupedBgSecondary = Color(0xFF1C1C1E),
    groupedBgTertiary = Color(0xFF2C2C2E),
)

// ══════════════════════════════════════════════════════════════════════════════
// Typography — SF Pro system
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class IOSTypography(
    val largeTitle: TextStyle = TextStyle(
        fontSize = 34.sp, fontWeight = FontWeight.Bold,
        letterSpacing = 0.37.sp, lineHeight = 41.sp,
    ),
    val title1: TextStyle = TextStyle(
        fontSize = 28.sp, fontWeight = FontWeight.Bold,
        letterSpacing = 0.36.sp, lineHeight = 34.sp,
    ),
    val title2: TextStyle = TextStyle(
        fontSize = 22.sp, fontWeight = FontWeight.Bold,
        letterSpacing = 0.35.sp, lineHeight = 28.sp,
    ),
    val title3: TextStyle = TextStyle(
        fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.38.sp, lineHeight = 25.sp,
    ),
    val headline: TextStyle = TextStyle(
        fontSize = 17.sp, fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.41).sp, lineHeight = 22.sp,
    ),
    val body: TextStyle = TextStyle(
        fontSize = 17.sp, fontWeight = FontWeight.Normal,
        letterSpacing = (-0.41).sp, lineHeight = 22.sp,
    ),
    val callout: TextStyle = TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Normal,
        letterSpacing = (-0.32).sp, lineHeight = 21.sp,
    ),
    val subheadline: TextStyle = TextStyle(
        fontSize = 15.sp, fontWeight = FontWeight.Normal,
        letterSpacing = (-0.24).sp, lineHeight = 20.sp,
    ),
    val footnote: TextStyle = TextStyle(
        fontSize = 13.sp, fontWeight = FontWeight.Normal,
        letterSpacing = (-0.08).sp, lineHeight = 18.sp,
    ),
    val caption1: TextStyle = TextStyle(
        fontSize = 12.sp, fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp, lineHeight = 16.sp,
    ),
    val caption2: TextStyle = TextStyle(
        fontSize = 11.sp, fontWeight = FontWeight.Normal,
        letterSpacing = 0.07.sp, lineHeight = 13.sp,
    ),
)

// ══════════════════════════════════════════════════════════════════════════════
// Shapes
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class IOSShapes(
    val xs: Shape = RoundedCornerShape(8.dp),
    val sm: Shape = RoundedCornerShape(12.dp),
    val md: Shape = RoundedCornerShape(16.dp),
    val lg: Shape = RoundedCornerShape(20.dp),
    val xl: Shape = RoundedCornerShape(28.dp),
    val capsule: Shape = CircleShape,
    val listRow: Shape = RoundedCornerShape(10.dp),
)

// ══════════════════════════════════════════════════════════════════════════════
// Spacing
// ══════════════════════════════════════════════════════════════════════════════

@Immutable
data class IOSSpacing(
    val xxS: Double = 2.0,
    val xS: Double = 4.0,
    val sm: Double = 8.0,
    val md: Double = 12.0,
    val lg: Double = 16.0,
    val xl: Double = 20.0,
    val xxL: Double = 24.0,
    val xxxL: Double = 32.0,
)

// ══════════════════════════════════════════════════════════════════════════════
// Theme Composition Locals
// ══════════════════════════════════════════════════════════════════════════════

val LocalIOSColors = staticCompositionLocalOf { LightColors }
val LocalIOSTypography = staticCompositionLocalOf { IOSTypography() }
val LocalIOSShapes = staticCompositionLocalOf { IOSShapes() }
val LocalIOSSpacing = staticCompositionLocalOf { IOSSpacing() }
val LocalIsDark = staticCompositionLocalOf { false }

@Stable
object IOSTheme {
    val colors: IOSColors @Composable get() = LocalIOSColors.current
    val typography: IOSTypography @Composable get() = LocalIOSTypography.current
    val shapes: IOSShapes @Composable get() = LocalIOSShapes.current
    val spacing: IOSSpacing @Composable get() = LocalIOSSpacing.current
    val isDark: Boolean @Composable get() = LocalIsDark.current
}

@Composable
fun IOSTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    androidx.compose.runtime.CompositionLocalProvider(
        LocalIOSColors provides colors,
        LocalIOSTypography provides IOSTypography(),
        LocalIOSShapes provides IOSShapes(),
        LocalIOSSpacing provides IOSSpacing(),
        LocalIsDark provides darkTheme,
    ) {
        content()
    }
}
