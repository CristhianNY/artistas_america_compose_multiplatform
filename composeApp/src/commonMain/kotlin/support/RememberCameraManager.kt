package support

import androidx.compose.runtime.Composable

// CameraManager.kt (commonMain)
@Composable
expect fun rememberCameraManager(onImageCaptured: (ByteArray?) -> Unit): CameraManager

expect class CameraManager {
    fun launch()
}
