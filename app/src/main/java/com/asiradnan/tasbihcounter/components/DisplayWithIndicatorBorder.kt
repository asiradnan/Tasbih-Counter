package com.asiradnan.tasbihcounter.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DisplayWithProgressBorder(
    modifier: Modifier = Modifier,
    count: Int,
    progress: Float,
    textModifier: Modifier = Modifier, // Separate modifier for the Text element if needed
    progressBorderWidthDp: Dp = 3.dp,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    trackIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 200),
        label = "displayProgressAnimation"
    )

    OutlinedCard(
        modifier = modifier // Apply the main modifier to the OutlinedCard
            .fillMaxWidth(0.8f)
            .drawWithContent {
                // 1. Draw card content first (background and the Text composable)
                drawContent()

                val strokeWidthPx = progressBorderWidthDp.toPx()
                val halfStroke = strokeWidthPx / 2
                val cardWidth = size.width
                val cardHeight = size.height

                // Define the points of the rectangle's perimeter for the stroke
                val topLeft = Offset(halfStroke, halfStroke)
                val topRight = Offset(cardWidth - halfStroke, halfStroke)
                val bottomRight = Offset(cardWidth - halfStroke, cardHeight - halfStroke)
                val bottomLeft = Offset(halfStroke, cardHeight - halfStroke)

                // Total perimeter length
                // Ensure dimensions are positive before calculating perimeter
                val effectiveWidth = (cardWidth - strokeWidthPx).coerceAtLeast(0f)
                val effectiveHeight = (cardHeight - strokeWidthPx).coerceAtLeast(0f)
                val perimeter = (effectiveWidth * 2) + (effectiveHeight * 2)

                if (perimeter <= 0f) return@drawWithContent // Avoid drawing if card is too small

                val progressLength = perimeter * animatedProgress.coerceIn(0f, 1f)


                // --- Draw Track ---
                if (trackIndicatorColor != Color.Transparent) {
                    val trackPath = Path().apply {
                        moveTo(topLeft.x, topLeft.y)
                        lineTo(topRight.x, topRight.y)
                        lineTo(bottomRight.x, bottomRight.y)
                        lineTo(bottomLeft.x, bottomLeft.y)
                        close()
                    }
                    drawPath(
                        path = trackPath,
                        color = trackIndicatorColor,
                        style = Stroke(width = strokeWidthPx, join = StrokeJoin.Miter)
                    )
                }

                // --- Draw Progress Path ---
                val progressPath = Path()
                var currentDrawnLength = 0f

                // Top edge
                progressPath.moveTo(topLeft.x, topLeft.y)
                var edgeLength = effectiveWidth
                if (currentDrawnLength + edgeLength >= progressLength) {
                    if (progressLength > currentDrawnLength) { // Only draw if there's something to draw
                        progressPath.lineTo(topLeft.x + (progressLength - currentDrawnLength), topLeft.y)
                    }
                } else {
                    progressPath.lineTo(topRight.x, topRight.y)
                    currentDrawnLength += edgeLength

                    // Right edge
                    edgeLength = effectiveHeight
                    if (currentDrawnLength < progressLength) {
                        if (currentDrawnLength + edgeLength >= progressLength) {
                            progressPath.lineTo(topRight.x, topRight.y + (progressLength - currentDrawnLength))
                        } else {
                            progressPath.lineTo(bottomRight.x, bottomRight.y)
                            currentDrawnLength += edgeLength

                            // Bottom edge
                            edgeLength = effectiveWidth
                            if (currentDrawnLength < progressLength) {
                                if (currentDrawnLength + edgeLength >= progressLength) {
                                    progressPath.lineTo(bottomRight.x - (progressLength - currentDrawnLength), bottomRight.y)
                                } else {
                                    progressPath.lineTo(bottomLeft.x, bottomLeft.y)
                                    currentDrawnLength += edgeLength

                                    // Left edge
                                    edgeLength = effectiveHeight
                                    if (currentDrawnLength < progressLength && currentDrawnLength + edgeLength >= progressLength) {
                                        progressPath.lineTo(bottomLeft.x, bottomLeft.y - (progressLength - currentDrawnLength))
                                    }
                                }
                            }
                        }
                    }
                }

                if (!progressPath.isEmpty) {
                    drawPath(
                        path = progressPath,
                        color = progressIndicatorColor,
                        style = Stroke(
                            width = strokeWidthPx,
                            cap = StrokeCap.Butt,
                            join = StrokeJoin.Miter
                        )
                    )
                }
            },
        shape = RectangleShape,
        colors = CardDefaults.outlinedCardColors(containerColor = cardBackgroundColor),
        border = CardDefaults.outlinedCardBorder(true).copy(width = 1.dp)
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = textModifier // Apply the separate textModifier here
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        )
    }
}