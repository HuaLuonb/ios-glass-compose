package com.iosglass.core

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * iOS 26 Liquid Glass Material Engine
 *
 * AGSL (Android Graphics Shading Language) shader for GPU-accelerated
 * glass effects matching Apple's Liquid Glass material system.
 */
@Immutable
data class GlassMaterialSpec(
    val tint: Color = Color.White.copy(alpha = 0.12f),
    val blurRadius: Dp = 30.dp,
    val borderOpacity: Float = 0.25f,
    val specularIntensity: Float = 0.5f,
    val specularAngle: Float = 145f,
    val cornerRadius: Dp = 20.dp,
    val elevation: Dp = 0.dp,
    val isElevated: Boolean = false,
    val saturation: Float = 1.8f,
    val refraction: Float = 0.02f,
)

@Stable
object GlassMaterials {
    /** Standard — cards, general containers */
    val regular = GlassMaterialSpec()

    /** Thick — nav bars, tab bars, toolbars */
    val thick = GlassMaterialSpec(
        tint = Color.White.copy(alpha = 0.22f),
        blurRadius = 50.dp,
        borderOpacity = 0.35f,
        saturation = 2.0f,
    )

    /** Thin — tooltips, subtle overlays */
    val thin = GlassMaterialSpec(
        tint = Color.White.copy(alpha = 0.06f),
        blurRadius = 15.dp,
        borderOpacity = 0.12f,
        specularIntensity = 0.2f,
    )

    /** Dark variant */
    val dark = GlassMaterialSpec(
        tint = Color.Black.copy(alpha = 0.3f),
        blurRadius = 30.dp,
        borderOpacity = 0.15f,
        specularIntensity = 0.25f,
        saturation = 1.5f,
    )

    /** Elevated — modals, alerts, action sheets */
    val elevated = GlassMaterialSpec(
        tint = Color.White.copy(alpha = 0.65f),
        blurRadius = 60.dp,
        borderOpacity = 0.45f,
        specularIntensity = 0.7f,
        isElevated = true,
        elevation = 24.dp,
    )

    /** Ultra thin — search bars, chips */
    val ultraThin = GlassMaterialSpec(
        tint = Color.White.copy(alpha = 0.04f),
        blurRadius = 10.dp,
        borderOpacity = 0.08f,
        specularIntensity = 0.1f,
        saturation = 1.2f,
    )
}

/**
 * AGSL Shader for Liquid Glass refraction effect
 */
private const val GLASS_SHADER = """
    uniform shader blurred;
    uniform float2 size;
    uniform float refraction;
    uniform float specularIntensity;
    uniform float specularAngle;
    uniform float4 tint;

    half4 main(float2 coord) {
        // Edge-based refraction
        float2 center = size * 0.5;
        float2 delta = (coord - center) / size;
        float dist = length(delta);
        float2 offset = delta * refraction * dist * size;
        float2 refracted = coord + offset;

        half4 base = blurred.eval(refracted);

        // Specular highlight
        float angle = radians(specularAngle);
        float2 lightDir = float2(cos(angle), sin(angle));
        float spec = pow(max(dot(normalize(delta), lightDir), 0.0), 3.0) * specularIntensity;
        half4 highlight = half4(1.0, 1.0, 1.0, spec * 0.3);

        // Blend tint
        half4 tinted = mix(base, half4(tint.rgb, 1.0), tint.a);

        return tinted + highlight;
    }
"""

/**
 * GPU-accelerated glass modifier using AGSL shaders
 */
@Composable
fun Modifier.glassEffect(
    spec: GlassMaterialSpec = GlassMaterials.regular,
    shape: Shape = RoundedCornerShape(spec.cornerRadius),
): Modifier {
    val shader = remember { RuntimeShader(GLASS_SHADER) }
    return this
        .graphicsLayer {
            // Use RenderEffect for GPU blur
            val blurEffect = RenderEffect.createBlurEffect(
                spec.blurRadius.toPx(),
                spec.blurRadius.toPx(),
                android.graphics.Shader.TileMode.CLAMP,
            )
            renderEffect = blurEffect
            clip = true
            shape?.let { this.clip = true }
        }
        .clip(shape)
        .drawWithContent {
            // Draw content first
            this.drawContent()

            // Specular overlay
            val angleRad = spec.specularAngle * PI.toFloat() / 180f
            val cx = size.width / 2f
            val cy = size.height / 2f
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = spec.specularIntensity * 0.25f),
                        Color.Transparent,
                        Color.White.copy(alpha = spec.specularIntensity * 0.08f),
                    ),
                    start = Offset(
                        cx + cos(angleRad) * size.width,
                        cy + sin(angleRad) * size.height,
                    ),
                    end = Offset(
                        cx - cos(angleRad) * size.width,
                        cy - sin(angleRad) * size.height,
                    ),
                ),
            )
        }
        .background(spec.tint, shape)
        .border(
            width = 0.5.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = spec.borderOpacity),
                    Color.White.copy(alpha = spec.borderOpacity * 0.4f),
                    Color.White.copy(alpha = spec.borderOpacity * 0.8f),
                ),
                start = Offset.Zero,
                end = Offset(size.width, size.height),
            ),
            shape = shape,
        )
}

/**
 * Simple blur-only modifier (no tint, no specular) for background blurs
 */
fun Modifier.backgroundBlur(
    radius: Dp = 30.dp,
): Modifier = this.graphicsLayer {
    renderEffect = RenderEffect.createBlurEffect(
        radius.toPx(), radius.toPx(),
        android.graphics.Shader.TileMode.CLAMP,
    )
}

/**
 * Glass Surface — reusable container
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    spec: GlassMaterialSpec = GlassMaterials.regular,
    shape: Shape = RoundedCornerShape(spec.cornerRadius),
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.glassEffect(spec, shape),
        content = content,
    )
}

/**
 * Modifier extension: saturation filter for vivid glass
 */
fun Modifier.saturation(value: Float = 1.8f): Modifier = this.graphicsLayer {
    val colorMatrix = android.graphics.ColorMatrix().apply {
        setSaturation(value)
    }
    renderEffect = RenderEffect.createColorFilterEffect(
        android.graphics.ColorMatrixColorFilter(colorMatrix),
    )
}
