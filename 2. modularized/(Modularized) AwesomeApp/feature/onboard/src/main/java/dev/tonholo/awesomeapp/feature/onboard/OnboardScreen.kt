package dev.tonholo.awesomeapp.feature.onboard

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import dev.tonholo.awesomeapp.feature.onboard.data.datastore.DataStoreManager
import dev.tonholo.awesomeapp.navigation.Routes
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardScreen(
    navController: NavController = rememberNavController(),
    viewModel: OnboardViewModel = hiltViewModel(),
) {
    val state by remember { viewModel.state }
    val pagerState = rememberPagerState()


    LaunchedEffect(state.targetPage) {
        if (state.targetPage >= state.content.size) {
            navController.navigate(Routes.Feed())
            viewModel.onNavigateToFeed()
        } else {
            pagerState.animateScrollToPage(page = state.targetPage)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onPageChanged(pagerState.currentPage)
    }

    AnimatedVisibility(
        visible = state.isReadyToShow,
        enter = fadeIn(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {

            HorizontalPager(
                count = state.content.size,
                state = pagerState,
            ) { page ->
                with(state.content[page]) {
                    OnboardPage(
                        painter = painterResource(id = resource),
                        title = title,
                        description = description,
                    )
                }
            }
            if (state.shouldShowSkipButton) {
                TextButton(
                    onClick = { viewModel.onSkipButtonClicked() },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp),
                ) {
                    Text(text = "Skip")
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (state.shouldShowBackButton) {
                    TextButton(
                        onClick = { viewModel.onBackButtonClicked() },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.weight(1f),
                    ) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Back")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                )

                TextButton(
                    onClick = { viewModel.onNextButtonClicked() },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = "Next")
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardPage(
    painter: Painter,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.9f),
            contentScale = ContentScale.FillWidth,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
        )
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
        OnboardScreen(
            viewModel = OnboardViewModel(DataStoreManager(LocalContext.current))
        )
    }
}
