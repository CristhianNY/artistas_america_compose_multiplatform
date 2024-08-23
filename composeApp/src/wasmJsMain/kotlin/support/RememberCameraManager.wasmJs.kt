package support

import androidx.compose.runtime.Composable

@Composable
actual fun rememberCameraManager(onImageCaptured: (ByteArray?) -> Unit): CameraManager {
    TODO("Not yet implemented")
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CameraManager {
    actual fun launch() {
    }
}