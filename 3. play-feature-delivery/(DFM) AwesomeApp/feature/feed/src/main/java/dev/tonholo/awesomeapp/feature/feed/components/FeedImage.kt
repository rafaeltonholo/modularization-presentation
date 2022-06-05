package dev.tonholo.awesomeapp.feature.feed.components

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tonholo.awesomeapp.data.model.UnsplashImage
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme

/**
 * Represents a single entry of an Unsplash Image. This will render the image and also the copyrights of it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedImage(
    image: UnsplashImage,
    modifier: Modifier = Modifier,
    onClick: (UnsplashImage) -> Unit = {},
    onFavClick: (UnsplashImage) -> Unit = {},
) {
    Card(
        onClick = { onClick(image) },
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .clickable { onClick(image) },
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.outlinedCardColors(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.urls?.full)
                    .crossfade(true)
                    .build(),
                contentDescription = image.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            )

            Row(
                modifier = Modifier
                    .fillMaxHeight(0.15f)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        MaterialTheme.colorScheme.onSurface,
                    )
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Photo by ${image.user?.name} on Unsplash",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.labelSmall,
                )
                IconButton(
                    onClick = { onFavClick(image) },
                ) {
                    Image(
                        imageVector = if (image.likedByUser) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Like",
                        colorFilter = ColorFilter.tint(
                            if (image.likedByUser) Color.Red
                            else MaterialTheme.colorScheme.inverseOnSurface
                        ),
                    )
                }
            }
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
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            repeat(10) {
                val image = UnsplashImage(
                    blurHash = "mock",
                    color = "mock",
                    createdAt = "mock",
                    currentUserCollections = emptyList(),
                    description = "mock",
                    height = 1000,
                    id = "mock",
                    likedByUser = it % 2 == 0,
                    likes = 100,
                    links = null,
                    updatedAt = "mock",
                    urls = null,
                    user = null,
                    width = 1000,
                    downloads = null,
                    exif = null,
                    location = null,
                    publicDomain = null,
                    tags = emptyList(),
                )

                FeedImage(
                    image = image,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
