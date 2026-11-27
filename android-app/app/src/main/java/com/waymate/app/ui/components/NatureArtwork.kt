package com.waymate.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.waymate.app.ui.theme.BorderWarm
import com.waymate.app.ui.theme.CreamBackground
import com.waymate.app.ui.theme.ForestGreen
import com.waymate.app.ui.theme.LeafOlive
import com.waymate.app.ui.theme.LeafSage
import com.waymate.app.ui.theme.MountainFar
import com.waymate.app.ui.theme.MountainFoothill
import com.waymate.app.ui.theme.MountainMid
import com.waymate.app.ui.theme.MountainNear
import com.waymate.app.ui.theme.PineDark
import com.waymate.app.ui.theme.PineLight
import com.waymate.app.ui.theme.PineMid
import com.waymate.app.ui.theme.SageOlive
import com.waymate.app.ui.theme.SkyMidWarm
import com.waymate.app.ui.theme.SkyTopBlue
import com.waymate.app.ui.theme.SunGlowCore
import com.waymate.app.ui.theme.SunGlowOuter
import com.waymate.app.ui.theme.VerifiedGreen
import com.waymate.app.ui.theme.VerifiedSoftBg

/**
 * Full-bleed mountain, sun, pine forest, and winding trail backdrop for the Welcome screen.
 */
@Composable
fun WelcomeMountainBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Sky gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    SkyTopBlue,
                    SkyMidWarm,
                    Color(0xFFDADFE2),
                    CreamBackground
                ),
                startY = 0f,
                endY = h * 0.85f
            )
        )

        // Warm morning sun glow
        val sunCenter = Offset(w * 0.72f, h * 0.14f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    SunGlowCore,
                    SunGlowOuter.copy(alpha = 0.65f),
                    Color.Transparent
                ),
                center = sunCenter,
                radius = w * 0.36f
            ),
            radius = w * 0.36f,
            center = sunCenter
        )
        drawCircle(
            color = SunGlowCore.copy(alpha = 0.9f),
            radius = w * 0.065f,
            center = sunCenter
        )

        // Far mountain ridge
        drawRidge(
            points = listOf(
                0f to h * 0.34f,
                w * 0.18f to h * 0.27f,
                w * 0.36f to h * 0.32f,
                w * 0.56f to h * 0.22f,
                w * 0.76f to h * 0.28f,
                w * 0.92f to h * 0.25f,
                w to h * 0.29f
            ),
            bottomY = h * 0.65f,
            brush = Brush.verticalGradient(
                colors = listOf(MountainFar.copy(alpha = 0.78f), Color(0xFFD9E1E5)),
                startY = h * 0.22f,
                endY = h * 0.65f
            )
        )

        // Mid mountain ridge
        drawRidge(
            points = listOf(
                0f to h * 0.42f,
                w * 0.22f to h * 0.34f,
                w * 0.44f to h * 0.41f,
                w * 0.68f to h * 0.31f,
                w * 0.88f to h * 0.37f,
                w to h * 0.35f
            ),
            bottomY = h * 0.72f,
            brush = Brush.verticalGradient(
                colors = listOf(MountainMid.copy(alpha = 0.88f), Color(0xFFC8D5DC)),
                startY = h * 0.31f,
                endY = h * 0.72f
            )
        )

        // Dashed journey trail & map pin on mid ridge
        val trailPath = Path().apply {
            moveTo(w * 0.10f, h * 0.47f)
            cubicTo(w * 0.28f, h * 0.39f, w * 0.44f, h * 0.48f, w * 0.66f, h * 0.365f)
        }
        drawPath(
            path = trailPath,
            color = Color.White.copy(alpha = 0.85f),
            style = Stroke(
                width = 2.5.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 12f), 0f)
            )
        )
        drawMapPin(center = Offset(w * 0.675f, h * 0.342f), radius = 10.dp.toPx())

        // Near mountain ridge
        drawRidge(
            points = listOf(
                0f to h * 0.56f,
                w * 0.16f to h * 0.49f,
                w * 0.38f to h * 0.58f,
                w * 0.62f to h * 0.50f,
                w * 0.84f to h * 0.44f,
                w to h * 0.51f
            ),
            bottomY = h * 0.84f,
            brush = Brush.verticalGradient(
                colors = listOf(MountainNear.copy(alpha = 0.85f), Color(0xFFD5DED7)),
                startY = h * 0.44f,
                endY = h * 0.84f
            )
        )

        // Foreground green foothills
        drawRidge(
            points = listOf(
                0f to h * 0.66f,
                w * 0.28f to h * 0.73f,
                w * 0.58f to h * 0.67f,
                w * 0.82f to h * 0.71f,
                w to h * 0.64f
            ),
            bottomY = h,
            brush = Brush.verticalGradient(
                colors = listOf(PineLight.copy(alpha = 0.75f), CreamBackground),
                startY = h * 0.64f,
                endY = h * 0.90f
            )
        )

        // Pine trees silhouette along lower left and right
        val leftPines = listOf(
            Triple(w * 0.04f, h * 0.74f, 0.95f),
            Triple(w * 0.11f, h * 0.77f, 1.15f),
            Triple(w * 0.19f, h * 0.76f, 0.85f),
            Triple(w * 0.27f, h * 0.79f, 0.75f),
            Triple(w * 0.34f, h * 0.80f, 0.60f)
        )
        leftPines.forEach { (x, y, s) ->
            drawPineTree(Offset(x, y), treeHeight = h * 0.15f * s, color = PineDark.copy(alpha = 0.92f))
        }

        val rightPines = listOf(
            Triple(w * 0.72f, h * 0.78f, 0.68f),
            Triple(w * 0.81f, h * 0.76f, 0.85f),
            Triple(w * 0.90f, h * 0.74f, 1.05f),
            Triple(w * 0.97f, h * 0.76f, 0.90f)
        )
        rightPines.forEach { (x, y, s) ->
            drawPineTree(Offset(x, y), treeHeight = h * 0.14f * s, color = PineMid.copy(alpha = 0.88f))
        }

        // Top-left foreground pine/fern branch
        drawTopLeftBranch(w, h)

        // Soft cream wash behind bottom buttons so CTAs stay crisp
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    CreamBackground.copy(alpha = 0.82f),
                    CreamBackground
                ),
                startY = h * 0.72f,
                endY = h * 0.93f
            )
        )
    }
}

/**
 * Curved misty mountain and pine-forest header banner used on Login, Auth Entry, and Create Account.
 */
@Composable
fun NatureHeaderBackground(
    modifier: Modifier = Modifier,
    height: Dp = 158.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height

        // Pale sky
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(SkyTopBlue.copy(alpha = 0.72f), Color(0xFFE7ECEA)),
                startY = 0f,
                endY = h
            )
        )

        // Far ridge
        drawRidge(
            points = listOf(
                0f to h * 0.50f,
                w * 0.18f to h * 0.30f,
                w * 0.38f to h * 0.46f,
                w * 0.60f to h * 0.24f,
                w * 0.80f to h * 0.40f,
                w to h * 0.30f
            ),
            bottomY = h,
            brush = Brush.verticalGradient(
                colors = listOf(MountainFar, MountainFar.copy(alpha = 0.55f)),
                startY = h * 0.24f,
                endY = h
            )
        )

        // Mid ridge
        drawRidge(
            points = listOf(
                0f to h * 0.62f,
                w * 0.24f to h * 0.44f,
                w * 0.48f to h * 0.60f,
                w * 0.72f to h * 0.38f,
                w * 0.92f to h * 0.55f,
                w to h * 0.48f
            ),
            bottomY = h,
            brush = Brush.verticalGradient(
                colors = listOf(MountainMid.copy(alpha = 0.88f), MountainNear.copy(alpha = 0.75f)),
                startY = h * 0.38f,
                endY = h
            )
        )

        // Pine trees framing left & right
        val pines = listOf(
            Triple(w * 0.03f, h * 0.82f, 0.92f) to PineDark,
            Triple(w * 0.09f, h * 0.85f, 0.78f) to PineMid,
            Triple(w * 0.15f, h * 0.86f, 0.65f) to PineDark,
            Triple(w * 0.22f, h * 0.89f, 0.52f) to PineLight,
            Triple(w * 0.79f, h * 0.89f, 0.55f) to PineLight,
            Triple(w * 0.86f, h * 0.86f, 0.72f) to PineMid,
            Triple(w * 0.92f, h * 0.84f, 0.90f) to PineDark,
            Triple(w * 0.98f, h * 0.82f, 0.80f) to PineMid
        )
        pines.forEach { (pos, color) ->
            drawPineTree(Offset(pos.first, pos.second), treeHeight = h * 0.46f * pos.third, color = color)
        }

        // Foreground foothill strip
        drawRidge(
            points = listOf(
                0f to h * 0.78f,
                w * 0.25f to h * 0.83f,
                w * 0.50f to h * 0.74f,
                w * 0.75f to h * 0.82f,
                w to h * 0.76f
            ),
            bottomY = h,
            brush = Brush.verticalGradient(
                colors = listOf(MountainFoothill.copy(alpha = 0.65f), PineMid.copy(alpha = 0.45f)),
                startY = h * 0.72f,
                endY = h
            )
        )

        // Organic curved cream wave overlay at the bottom of the header
        val wavePath = Path().apply {
            moveTo(0f, h * 0.80f)
            cubicTo(w * 0.26f, h * 0.96f, w * 0.68f, h * 0.66f, w, h * 0.84f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(path = wavePath, color = CreamBackground)
    }
}

/**
 * Bottom botanical foliage & subtle mountain haze framing every screen.
 */
@Composable
fun BotanicalFooterDecoration(
    modifier: Modifier = Modifier,
    height: Dp = 96.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height

        // Subtle misty bottom mountain silhouette
        drawRidge(
            points = listOf(
                0f to h * 0.72f,
                w * 0.25f to h * 0.42f,
                w * 0.52f to h * 0.66f,
                w * 0.76f to h * 0.36f,
                w to h * 0.58f
            ),
            bottomY = h,
            brush = Brush.verticalGradient(
                colors = listOf(MountainFar.copy(alpha = 0.28f), CreamBackground.copy(alpha = 0.1f)),
                startY = h * 0.35f,
                endY = h
            )
        )

        // Left botanical fern/leaf sprigs
        drawBotanicalSprig(
            origin = Offset(w * 0.04f, h),
            tip = Offset(w * 0.16f, h * 0.18f),
            leafCount = 5,
            leafColor = LeafOlive.copy(alpha = 0.82f),
            scale = 1f
        )
        drawBotanicalSprig(
            origin = Offset(w * 0.14f, h),
            tip = Offset(w * 0.24f, h * 0.42f),
            leafCount = 4,
            leafColor = LeafSage.copy(alpha = 0.75f),
            scale = 0.8f
        )

        // Right botanical fern/leaf sprigs
        drawBotanicalSprig(
            origin = Offset(w * 0.96f, h),
            tip = Offset(w * 0.84f, h * 0.16f),
            leafCount = 5,
            leafColor = LeafOlive.copy(alpha = 0.82f),
            scale = 1f
        )
        drawBotanicalSprig(
            origin = Offset(w * 0.86f, h),
            tip = Offset(w * 0.76f, h * 0.44f),
            leafCount = 4,
            leafColor = LeafSage.copy(alpha = 0.75f),
            scale = 0.8f
        )
    }
}

/**
 * Clean 4-color Google 'G' mark drawn with Canvas arcs.
 */
@Composable
fun GoogleGIcon(modifier: Modifier = Modifier.size(20.dp)) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.22f
        val inset = strokeWidth / 2f
        val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
        val topLeft = Offset(inset, inset)

        // Blue top-right & crossbar
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -15f,
            sweepAngle = 105f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        // Green bottom
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 88f,
            sweepAngle = 95f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        // Yellow left
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 180f,
            sweepAngle = 62f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        // Red top
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 240f,
            sweepAngle = 92f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        // Horizontal bar
        val barY = size.height * 0.5f
        drawLine(
            color = Color(0xFF4285F4),
            start = Offset(size.width * 0.50f, barY),
            end = Offset(size.width * 0.94f, barY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Butt
        )
    }
}

enum class AuthEmblemType {
    ENVELOPE,
    ENVELOPE_QUESTION,
    ENVELOPE_CHECK,
    PADLOCK,
    SUCCESS_BADGE
}

@Composable
fun AuthHeaderEmblem(
    type: AuthEmblemType,
    modifier: Modifier = Modifier.size(72.dp)
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val stroke = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)

            when (type) {
                AuthEmblemType.ENVELOPE,
                AuthEmblemType.ENVELOPE_QUESTION,
                AuthEmblemType.ENVELOPE_CHECK -> {
                    // Subtle accent lines above open envelope on plain envelope
                    if (type == AuthEmblemType.ENVELOPE) {
                        drawLine(ForestGreen, Offset(w * 0.50f, h * 0.08f), Offset(w * 0.50f, h * 0.16f), 2.dp.toPx(), StrokeCap.Round)
                        drawLine(ForestGreen, Offset(w * 0.34f, h * 0.13f), Offset(w * 0.39f, h * 0.19f), 2.dp.toPx(), StrokeCap.Round)
                        drawLine(ForestGreen, Offset(w * 0.66f, h * 0.13f), Offset(w * 0.61f, h * 0.19f), 2.dp.toPx(), StrokeCap.Round)
                    }

                    val boxLeft = w * 0.14f
                    val boxTop = h * 0.30f
                    val boxW = w * 0.72f
                    val boxH = h * 0.48f

                    // Envelope body fill & border
                    drawRoundRect(
                        color = Color(0xFFE7EDE4),
                        topLeft = Offset(boxLeft, boxTop),
                        size = Size(boxW, boxH),
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                    drawRoundRect(
                        color = ForestGreen,
                        topLeft = Offset(boxLeft, boxTop),
                        size = Size(boxW, boxH),
                        cornerRadius = CornerRadius(8.dp.toPx()),
                        style = stroke
                    )
                    // Flap lines
                    val flap = Path().apply {
                        moveTo(boxLeft + 2.dp.toPx(), boxTop + 2.dp.toPx())
                        lineTo(w * 0.50f, boxTop + boxH * 0.58f)
                        lineTo(boxLeft + boxW - 2.dp.toPx(), boxTop + 2.dp.toPx())
                    }
                    drawPath(flap, color = ForestGreen, style = stroke)

                    if (type == AuthEmblemType.ENVELOPE_CHECK || type == AuthEmblemType.ENVELOPE_QUESTION) {
                        val badgeCenter = Offset(w * 0.77f, h * 0.72f)
                        val badgeRadius = w * 0.16f
                        drawCircle(color = VerifiedGreen, radius = badgeRadius, center = badgeCenter)
                        drawCircle(color = CreamBackground, radius = badgeRadius, center = badgeCenter, style = Stroke(2.dp.toPx()))
                        if (type == AuthEmblemType.ENVELOPE_CHECK) {
                            val check = Path().apply {
                                moveTo(badgeCenter.x - badgeRadius * 0.42f, badgeCenter.y + badgeRadius * 0.05f)
                                lineTo(badgeCenter.x - badgeRadius * 0.08f, badgeCenter.y + badgeRadius * 0.38f)
                                lineTo(badgeCenter.x + badgeRadius * 0.46f, badgeCenter.y - badgeRadius * 0.30f)
                            }
                            drawPath(check, color = Color.White, style = Stroke(2.2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
                        } else {
                            // Question mark dot + curve
                            drawArc(
                                color = Color.White,
                                startAngle = 200f,
                                sweepAngle = 230f,
                                useCenter = false,
                                topLeft = Offset(badgeCenter.x - badgeRadius * 0.28f, badgeCenter.y - badgeRadius * 0.45f),
                                size = Size(badgeRadius * 0.56f, badgeRadius * 0.52f),
                                style = Stroke(2.dp.toPx(), cap = StrokeCap.Round)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 1.6.dp.toPx(),
                                center = Offset(badgeCenter.x, badgeCenter.y + badgeRadius * 0.38f)
                            )
                        }
                    }
                }

                AuthEmblemType.PADLOCK -> {
                    val shackleW = w * 0.36f
                    val shackleH = h * 0.30f
                    drawArc(
                        color = ForestGreen,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset((w - shackleW) / 2f, h * 0.14f),
                        size = Size(shackleW, shackleH),
                        style = Stroke(width = 2.6.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawLine(
                        color = ForestGreen,
                        start = Offset((w - shackleW) / 2f, h * 0.29f),
                        end = Offset((w - shackleW) / 2f, h * 0.40f),
                        strokeWidth = 2.6.dp.toPx()
                    )
                    drawLine(
                        color = ForestGreen,
                        start = Offset((w + shackleW) / 2f, h * 0.29f),
                        end = Offset((w + shackleW) / 2f, h * 0.40f),
                        strokeWidth = 2.6.dp.toPx()
                    )
                    drawRoundRect(
                        color = VerifiedSoftBg,
                        topLeft = Offset(w * 0.22f, h * 0.38f),
                        size = Size(w * 0.56f, h * 0.46f),
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                    drawRoundRect(
                        color = ForestGreen,
                        topLeft = Offset(w * 0.22f, h * 0.38f),
                        size = Size(w * 0.56f, h * 0.46f),
                        cornerRadius = CornerRadius(10.dp.toPx()),
                        style = stroke
                    )
                    drawCircle(color = ForestGreen, radius = 3.2.dp.toPx(), center = Offset(w * 0.50f, h * 0.57f))
                    drawLine(
                        color = ForestGreen,
                        start = Offset(w * 0.50f, h * 0.59f),
                        end = Offset(w * 0.50f, h * 0.70f),
                        strokeWidth = 2.4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                AuthEmblemType.SUCCESS_BADGE -> {
                    val center = Offset(w * 0.50f, h * 0.50f)
                    drawCircle(color = VerifiedSoftBg.copy(alpha = 0.7f), radius = w * 0.44f, center = center)
                    drawCircle(color = Color(0xFFCDE1C8), radius = w * 0.34f, center = center)
                    val check = Path().apply {
                        moveTo(center.x - w * 0.14f, center.y + h * 0.02f)
                        lineTo(center.x - w * 0.03f, center.y + h * 0.14f)
                        lineTo(center.x + w * 0.17f, center.y - h * 0.11f)
                    }
                    drawPath(
                        path = check,
                        color = ForestGreen,
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                    // Subtle sparkles
                    drawCircle(SageOlive, 2.5.dp.toPx(), Offset(w * 0.12f, h * 0.24f))
                    drawCircle(Color(0xFFD1B464), 2.5.dp.toPx(), Offset(w * 0.88f, h * 0.22f))
                    drawCircle(VerifiedGreen, 2.dp.toPx(), Offset(w * 0.86f, h * 0.78f))
                }
            }
        }
    }
}

private fun DrawScope.drawRidge(
    points: List<Pair<Float, Float>>,
    bottomY: Float,
    brush: Brush
) {
    val path = Path().apply {
        moveTo(0f, bottomY)
        points.forEach { (x, y) -> lineTo(x, y) }
        lineTo(size.width, bottomY)
        close()
    }
    drawPath(path = path, brush = brush)
}

private fun DrawScope.drawPineTree(base: Offset, treeHeight: Float, color: Color) {
    val halfW = treeHeight * 0.28f
    // Trunk
    drawLine(
        color = color,
        start = base,
        end = Offset(base.x, base.y - treeHeight * 0.22f),
        strokeWidth = (treeHeight * 0.055f).coerceAtLeast(2f)
    )
    // 3 overlapping pine tiers
    val tiers = listOf(
        Triple(0.14f, 0.54f, 1.0f),
        Triple(0.36f, 0.78f, 0.80f),
        Triple(0.58f, 1.00f, 0.56f)
    )
    tiers.forEach { (bottomFrac, topFrac, widthFrac) ->
        val p = Path().apply {
            moveTo(base.x, base.y - treeHeight * topFrac)
            lineTo(base.x - halfW * widthFrac, base.y - treeHeight * bottomFrac)
            lineTo(base.x + halfW * widthFrac, base.y - treeHeight * bottomFrac)
            close()
        }
        drawPath(path = p, color = color)
    }
}

private fun DrawScope.drawMapPin(center: Offset, radius: Float) {
    val pinPath = Path().apply {
        moveTo(center.x, center.y + radius * 1.45f)
        cubicTo(
            center.x - radius * 1.15f, center.y + radius * 0.4f,
            center.x - radius * 1.05f, center.y - radius * 1.05f,
            center.x, center.y - radius * 1.05f
        )
        cubicTo(
            center.x + radius * 1.05f, center.y - radius * 1.05f,
            center.x + radius * 1.15f, center.y + radius * 0.4f,
            center.x, center.y + radius * 1.45f
        )
        close()
    }
    drawPath(path = pinPath, color = Color.White)
    drawCircle(color = ForestGreen, radius = radius * 0.38f, center = Offset(center.x, center.y - radius * 0.15f))
}

private fun DrawScope.drawTopLeftBranch(w: Float, h: Float) {
    val branch = Path().apply {
        moveTo(-w * 0.02f, h * 0.14f)
        quadraticTo(w * 0.12f, h * 0.12f, w * 0.24f, h * 0.04f)
    }
    drawPath(branch, color = PineDark.copy(alpha = 0.85f), style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round))
    val tufts = listOf(
        Offset(w * 0.05f, h * 0.125f) to -25f,
        Offset(w * 0.11f, h * 0.105f) to -15f,
        Offset(w * 0.17f, h * 0.08f) to -35f,
        Offset(w * 0.22f, h * 0.045f) to -20f,
        Offset(w * 0.04f, h * 0.18f) to 18f,
        Offset(w * 0.09f, h * 0.16f) to 25f
    )
    tufts.forEach { (pt, angle) ->
        rotate(degrees = angle, pivot = pt) {
            drawOval(
                color = PineDark.copy(alpha = 0.88f),
                topLeft = Offset(pt.x - 16.dp.toPx(), pt.y - 6.dp.toPx()),
                size = Size(32.dp.toPx(), 12.dp.toPx())
            )
        }
    }
}

private fun DrawScope.drawBotanicalSprig(
    origin: Offset,
    tip: Offset,
    leafCount: Int,
    leafColor: Color,
    scale: Float
) {
    val ctrl = Offset((origin.x + tip.x) * 0.5f, origin.y * 0.65f + tip.y * 0.35f)
    val stem = Path().apply {
        moveTo(origin.x, origin.y)
        quadraticTo(ctrl.x, ctrl.y, tip.x, tip.y)
    }
    drawPath(stem, color = leafColor, style = Stroke(width = 1.6.dp.toPx() * scale, cap = StrokeCap.Round))

    for (i in 1..leafCount) {
        val t = i.toFloat() / (leafCount + 0.5f)
        val x = (1 - t) * (1 - t) * origin.x + 2 * (1 - t) * t * ctrl.x + t * t * tip.x
        val y = (1 - t) * (1 - t) * origin.y + 2 * (1 - t) * t * ctrl.y + t * t * tip.y
        val leafLen = (14.dp.toPx() * scale) * (1f - t * 0.35f)
        val leafW = 6.5.dp.toPx() * scale

        rotate(degrees = -38f, pivot = Offset(x, y)) {
            drawOval(
                color = leafColor,
                topLeft = Offset(x - leafLen, y - leafW / 2f),
                size = Size(leafLen, leafW)
            )
        }
        rotate(degrees = 38f, pivot = Offset(x, y)) {
            drawOval(
                color = leafColor,
                topLeft = Offset(x, y - leafW / 2f),
                size = Size(leafLen, leafW)
            )
        }
    }
}
