package dev.tonholo.awesomeapp.feature.camera.shape

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme

class CameraControlShape(
    private val cornerRadius: Dp,
): Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
        Outline.Generic(
            path = createPath(size, density)
        )

    private fun createPath(size: Size, density: Density): Path {
        fun Dp.toFloat() = with(density) {
            toPx()
        }
        return Path().apply {
            val cornerRadiusPx = cornerRadius.toFloat()

            arcTo(
                Rect(
                    left = 0f,
                    top = 0f,
                    right = cornerRadiusPx,
                    bottom = cornerRadiusPx,
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false,
            )
            lineTo(x = size.width - cornerRadiusPx, y = cornerRadiusPx)

            arcTo(
                Rect(
                    left = size.width - cornerRadiusPx,
                    top = 0f,
                    right = size.width,
                    bottom = cornerRadiusPx,
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false,
            )
            lineTo(x = size.width, y = size.height)
            lineTo(x = 0f, y = size.height)

        }
    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun Preview() {
    Preview(false)
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DarkPreview() {
    Preview(true)
}

@Composable
private fun Preview(
    darkMode: Boolean,
) {
    AwesomeAppTheme(darkTheme = darkMode) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background, CameraControlShape(32.dp))
                .fillMaxWidth()
                .height(72.dp)
        )
    }
}
