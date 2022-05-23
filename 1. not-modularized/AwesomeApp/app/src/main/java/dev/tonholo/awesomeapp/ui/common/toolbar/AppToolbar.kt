package dev.tonholo.awesomeapp.ui.common.toolbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme

@Composable
fun AppToolbar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: (() -> Unit)? = null,
    containerColor: Color? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = { onNavigationIconClick?.invoke() }) {
                    Icon(imageVector = it, contentDescription = null)
                }
            }
        },
        colors = containerColor?.let {
            TopAppBarDefaults.smallTopAppBarColors(
                containerColor = it,
                titleContentColor = contentColorFor(backgroundColor = it)
            )
        } ?: TopAppBarDefaults.smallTopAppBarColors(),
        actions = actions,
    )
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
        AppToolbar("Preview toolbar")
    }
}
