package support

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import android.provider.Settings
// PermissionsManager.android.kt


actual class PermissionsManager actual constructor(private val callback: PermissionCallback) : PermissionHandler {


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun askPermission(permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current
        when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                LaunchedEffect(cameraPermissionState) {
                    if (cameraPermissionState.status.isGranted) {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    } else {
                        lifecycleOwner.lifecycleScope.launch {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }
                }
            }
            PermissionType.GALLERY -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED) // No se requiere permiso explícito para galería en Android
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> rememberPermissionState(Manifest.permission.CAMERA).status.isGranted
            PermissionType.GALLERY -> true // Siempre concedido en Android
        }
    }

    @Composable
    override fun launchSettings() {
        val context = LocalContext.current
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}
