package dev.tonholo.awesomeapp.feature.camera.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat

private const val TAG = "CameraPermission"

sealed interface PermissionState {
    object Granted : PermissionState
    object Denied : PermissionState
}

@Composable
private fun requestPermissionLauncher(
    permissionAction: (PermissionState) -> Unit,
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
) { isGranted ->
    permissionAction(if (isGranted) PermissionState.Granted else PermissionState.Denied)
}

@Composable
private fun RequestCameraPermission(
    context: Context,
    permissionAction: (PermissionState) -> Unit,
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ->
            permissionAction(PermissionState.Granted)
        else -> {
            val launcher = requestPermissionLauncher(permissionAction)
            SideEffect {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}

@Composable
private fun RequestFilePermission(
    context: Context,
    permissionAction: (PermissionState) -> Unit,
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ->
            permissionAction(PermissionState.Granted)
        else -> {
            val launcher = requestPermissionLauncher(permissionAction)
            SideEffect {
                launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}

@Composable
@SuppressLint("ComposableNaming")
fun requestPermissions(context: Context) {
    RequestCameraPermission(context) {
        Log.i(TAG, "requestPermissions: permission state: $it")
    }
    RequestFilePermission(context) {
        Log.i(TAG, "requestPermissions: permission state: $it")
    }
}
