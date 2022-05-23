package dev.tonholo.awesomeapp.features.imagedetail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tonholo.awesomeapp.common.toReadableDate
import dev.tonholo.awesomeapp.data.remote.fake.FakeUnsplashApi
import dev.tonholo.awesomeapp.features.imagedetail.usecase.LoadImageDetailUseCase
import dev.tonholo.awesomeapp.ui.common.toolbar.AppToolbar
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    imageId: String,
    navController: NavController,
    viewModel: ImageDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadDetails(imageId)
    }
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Image details",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationIconClick = {
                    navController.popBackStack()
                },
            )
        },
    ) { padding ->
        AnimatedVisibility(
            visible = state.image != null,
            enter = fadeIn(),
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 64.dp)
            ) {
                val image = state.image
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image?.urls?.full)
                            .crossfade(true)
                            .build(),
                        contentDescription = image?.description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 24.dp)
                            .offset(y = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                            ),
                        ) {
                            Image(
                                imageVector = if (image?.likedByUser == true) {
                                    Icons.Filled.Favorite
                                } else Icons.Filled.FavoriteBorder,
                                contentDescription = "Like",
                                colorFilter = ColorFilter.tint(
                                    contentColorFor(MaterialTheme.colorScheme.primaryContainer)
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = image?.likes?.toString() ?: "0",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    image?.description?.let { description ->
                        Text(
                            text = description,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(end = 36.dp)
                        )
                    }
                    image?.user?.let {
                        Text(
                            text = "by ${it.name} on Unsplash",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    image?.location?.let {
                        Row {
                            Image(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location",
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                            )
                            val text = when {
                                !it.city.isNullOrEmpty() && !it.country.isNullOrEmpty() ->
                                    "Take on ${it.city}, ${it.country}"
                                !it.city.isNullOrEmpty() || !it.country.isNullOrEmpty() ->
                                    "Take on ${it.city ?: it.country}"
                                else -> "No location provided"
                            }
                            Text(text = text)
                        }
                    }

                    image?.tags?.let { tags ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tags",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        if (tags.isEmpty()) {
                            Text("No tags assigned to this image.")
                        } else {
                            LazyRow {
                                items(tags) {
                                    SuggestionChip(
                                        onClick = { },
                                        label = {
                                            Text(it.title)
                                        },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    image?.let { image ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Image information",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        ListOfItems {
                            Text(text = "Created at ${image.createdAt.toReadableDate()}")
                            Text(text = "Updated at ${image.updatedAt.toReadableDate()}")
                            Text(text = "${image.downloads ?: 0} Downloads")
                        }
                    }

                    image?.exif?.let {
                        if (it.name.isNullOrEmpty()) return@let

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Camera information",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        ListOfItems {
                            Text(
                                text = "📸 ${it.name}",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = "✨ ${it.exposureTime}",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = "${it.aperture} aperture",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = "${it.focalLength} focal length",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = "${it.iso} ISO",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun ListOfItems(content: @Composable ColumnScope.() -> Unit) =
    Column(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
        content()
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
        ImageDetailScreen(
            imageId = "Dwu85P9SOIk",
            navController = rememberNavController(),
            viewModel = ImageDetailViewModel(Dispatchers.Default, LoadImageDetailUseCase(FakeUnsplashApi))
        )
    }
}
