package dev.tonholo.awesomeapp.feature.camera

import android.content.res.Configuration
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.tonholo.awesomeapp.feature.camera.CameraScreen.PHOTO_URI_KEY
import dev.tonholo.awesomeapp.feature.camera.permissions.requestPermissions
import dev.tonholo.awesomeapp.feature.camera.shape.CameraControlShape
import dev.tonholo.awesomeapp.feature.camera.util.cameraProvider
import dev.tonholo.awesomeapp.feature.camera.util.getPhotoOutputDirectory
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme
import androidx.camera.core.Preview as CameraPreview

object CameraScreen {
    const val PHOTO_URI_KEY = "photo_uri"
}

@Composable
fun CameraScreen(
    navController: NavController = rememberNavController(),
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = remember { CameraPreview.Builder().build() }
    val previewView = remember { PreviewView(context) }
    val cameraSelector by remember {
        derivedStateOf {
            CameraSelector.Builder()
                .requireLensFacing(
                    if (state.isRearCamera) CameraSelector.LENS_FACING_FRONT
                    else CameraSelector.LENS_FACING_BACK
                )
                .build()
        }
    }
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    requestPermissions(context)

    LaunchedEffect(state.isRearCamera) {
        context.cameraProvider().also { cameraProvider ->
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
            )
            preview.setSurfaceProvider(previewView.surfaceProvider)
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val containerModifier = Modifier
            .fillMaxWidth()
            .height(this.maxHeight - 96.dp)

        state.uri?.let { uri ->
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = containerModifier
                    .background(Color.Black)
            )
        } ?: AndroidView(
            factory = { previewView },
            modifier = containerModifier,
        )
        CameraControls(
            navController,
            uri = state.uri,
            onFlipCameraClick = {
                viewModel.onFlipCameraClick()
            },
            onTakePictureClick = {
                viewModel.takePhoto(imageCapture, context.getPhotoOutputDirectory())
            },
            onDiscardPhotoClick = {
                state.uri?.let { viewModel.discardPhoto(it) }
            },
            onUsePhotoClick = { uri ->
                navController.currentBackStackEntry?.savedStateHandle?.set(PHOTO_URI_KEY, uri)
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun BoxScope.CameraControls(
    navController: NavController,
    onFlipCameraClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onDiscardPhotoClick: () -> Unit,
    onUsePhotoClick: (Uri) -> Unit,
    uri: Uri? = null,
) {
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(48.dp)
            .clip(CircleShape),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = .3f),
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
        ),
    ) {
        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Exit camera")
    }

    Row(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .background(MaterialTheme.colorScheme.background, CameraControlShape(32.dp))
            .height(128.dp)
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val baseButtonModifier = Modifier.size(48.dp)
        val buttonColor = MaterialTheme.colorScheme.onBackground
        if (uri == null) {
            IconButton(onClick = onFlipCameraClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_flip_camera_ios_36),
                    contentDescription = "Flip camera",
                    modifier = baseButtonModifier,
                    tint = buttonColor,
                )
            }
            IconButton(
                onClick = onTakePictureClick,
                modifier = Modifier.size(64.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_lens_36),
                    contentDescription = "Take a picture",
                    modifier = Modifier.size(64.dp),
                    tint = buttonColor,
                )
            }
        } else {
            IconButton(onClick = onDiscardPhotoClick) {
                Icon(
                    imageVector = Icons.Sharp.Delete,
                    contentDescription = "Discard photo",
                    modifier = baseButtonModifier,
                    tint = buttonColor,
                )
            }
            IconButton(
                onClick = { onUsePhotoClick(uri) },
                modifier = Modifier.size(64.dp),
            ) {
                Icon(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = "Use photo",
                    modifier = Modifier.size(64.dp),
                    tint = buttonColor,
                )
            }
        }
        Spacer(modifier = baseButtonModifier)
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
        CameraScreen()
    }
}
