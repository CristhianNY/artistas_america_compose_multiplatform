package support

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@Composable
actual fun rememberCameraManager(onImageCaptured: (ByteArray?) -> Unit): CameraManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                tempPhotoUri?.let { uri ->
                    coroutineScope.launch {
                        val imageBytes = getImageBytesFromUri(uri, contentResolver)
                        onImageCaptured(imageBytes)
                    }
                }
            } else {
                onImageCaptured(null)
            }
        }
    )
    return remember {
        CameraManager(
            onLaunch = {
                val photoUri = createTempImageUri(context)
                tempPhotoUri = photoUri
                cameraLauncher.launch(photoUri)
            }
        )
    }
}

actual class CameraManager constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}


fun createTempImageUri(context: Context): Uri {
    val tempFile = File.createTempFile("picture_${System.currentTimeMillis()}", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, tempFile)
}
