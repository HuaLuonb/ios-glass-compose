package com.iosglass.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * iOS 26 Animation System
 *
 * Exact spring/tween curves matching UIKit/CoreAnimation behavior.
 */
@Immutable
object IOSAnimation {

    // ── UIKit-matching bezier curves ──

    /** iOS default ease — general transitions */
    val DefaultEase = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1.0f)

    /** iOS ease-in — element enter */
    val EaseIn = CubicBezierEasing(0.42f, 0f, 1f, 1f)

    /** iOS ease-out — element exit */
    val EaseOut = CubicBezierEasing(0f, 0f, 0.58f, 1f)

    /** iOS ease-in-out — standard transition */
    val EaseInOut = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)

    /** iOS springy — slight overshoot */
    val Springy = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f)

    /** iOS smooth — no bounce */
    val Smooth = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

    // ── Spring specs (matching UIKit UISpringTimingParameters) ──

    /** Standard iOS spring — default interaction feedback */
    fun <T> iosSpring(): SpringSpec<T> = spring(
        dampingRatio = 0.82f,
        stiffness = 300f,
    )

    /** Bouncy spring — buttons, toggles */
    fun <T> bouncySpring(): SpringSpec<T> = spring(
        dampingRatio = 0.6f,
        stiffness = 400f,
    )

    /** Snappy spring — menus, popovers */
    fun <T> snappySpring(): SpringSpec<T> = spring(
        dampingRatio = 0.85f,
        stiffness = 600f,
    )

    /** Smooth spring — page transitions */
    fun <T> smoothSpring(): SpringSpec<T> = spring(
        dampingRatio = 1f,
        stiffness = 200f,
    )

    /** Gentle spring — large cards, modals */
    fun <T> gentleSpring(): SpringSpec<T> = spring(
        dampingRatio = 0.9f,
        stiffness = 150f,
    )

    /** Responsive spring — gesture tracking */
    fun <T> responsiveSpring(): SpringSpec<T> = spring(
        dampingRatio = 0.75f,
        stiffness = 500f,
    )

    // ── Duration constants ──

    object Duration {
        const val instant = 80
        const val fast = 180
        const val normal = 300
        const val slow = 450
        const val verySlow = 650
    }

    // ── Tween presets ──

    fun <T> iosTween(durationMs: Int = Duration.normal): AnimationSpec<T> =
        tween(durationMs, easing = DefaultEase)

    fun <T> fadeInOut(durationMs: Int = Duration.fast): AnimationSpec<T> =
        tween(durationMs, easing = EaseInOut)
}

// ╀── Composable animation helpers ──

@Composable
fun animateIOSPressScale(pressed: Boolean): State<Float> =
    animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = IOSAnimation.bouncySpring(),
        label = "press_scale",
    )

@Composable
fun animateIOSFade(visible: Boolean): State<Float> =
    animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = IOSAnimation.iosTween(),
        label = "fade",
    )

@Composable
fun animateIOSSlideY(visible: Boolean, offsetPx: Float = 60f): State<Float> =
    animateFloatAsState(
        targetValue = if (visible) 0f else offsetPx,
        animationSpec = IOSAnimation.iosSpring(),
        label = "slide_y",
    )

/**
 * Reusable press-scale modifier
 */
@Composable
fun Modifier.iosPressScale(pressed: Boolean): Modifier {
    val scale by animateIOSPressScale(pressed)
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}
