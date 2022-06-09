package dev.tonholo.awesomeapp.feature.feed

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManagerFactory
import dev.tonholo.awesomeapp.data.remote.fake.FakeUnsplashApi
import dev.tonholo.awesomeapp.di.dfm.fake.FakeDynamicFeatureLoaderManager
import dev.tonholo.awesomeapp.feature.feed.components.FeedImage
import dev.tonholo.awesomeapp.feature.feed.usecase.LoadImagesUseCase
import dev.tonholo.awesomeapp.feature.feed.usecase.RequestShoppingModuleUseCase
import dev.tonholo.awesomeapp.ui.common.toolbar.AppToolbar
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController = rememberNavController(),
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val expandedFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex <= 1
        }
    }

    LaunchedEffect(state.destinationTarget) {
        state.destinationTarget?.let { destination ->
            navController.navigate(destination)
            viewModel.onNavigateTriggered()
        }
    }

    state.shoppingModuleState?.let { shoppingModuleState ->
        AlertDialog(
            onDismissRequest = { /*no-op*/ },
            title = {
                Text(text = "Preparing shopping module")
            },
            text = {
                Column {
                    Text(
                        text = "Hi! We notice you don't have the shopping module installed. " +
                                "Don't you worry, we are preparing it for you!" +
                                "\nOnce it is installed, we are going to redirect you to it"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Status:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (shoppingModuleState) {
                            is ShoppingModuleState.Downloading ->
                                "Downloading ${shoppingModuleState.progress} of ${shoppingModuleState.totalBytes}"
                            ShoppingModuleState.Failure -> "Failed"
                            ShoppingModuleState.Installing -> "Installing module..."
                            ShoppingModuleState.Preparing -> "Preparing download..."
                        }
                    )
                }
            },
            confirmButton = {}
        )
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Feed",
                actions = {
                    IconButton(onClick = { viewModel.navigateToShopping() }) {
                        Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Shopping")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onFabClicked() },
                expanded = expandedFab,
                icon = { Icon(imageVector = Icons.Filled.Send, contentDescription = "Send your photo") },
                text = { Text(text = "Send your photo") }
            )
        },
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            item {
                AnimatedVisibility(visible = state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            items(state.images) { image ->
                FeedImage(
                    image = image,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 24.dp)
                        .fillMaxWidth(),
                    onClick = viewModel::onImageClicked,
                    onFavClick = viewModel::onImageFavClicked,
                )
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
        FeedScreen(
            viewModel = FeedViewModel(
                Dispatchers.IO,
                LoadImagesUseCase(FakeUnsplashApi),
                RequestShoppingModuleUseCase(
                    splitInstallManager = FakeSplitInstallManagerFactory.create(LocalContext.current),
                    dynamicFeatureLoaderManager = FakeDynamicFeatureLoaderManager,
                ),
            ),
        )
    }
}
