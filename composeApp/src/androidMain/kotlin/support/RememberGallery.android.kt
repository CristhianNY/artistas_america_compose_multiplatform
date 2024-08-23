package support
import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
actual fun rememberGalleryManager(onImageSelected: (ByteArray?) -> Unit): GalleryManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver

    // Usar rememberCoroutineScope para obtener un CoroutineScope asociado a la composición
    val coroutineScope = rememberCoroutineScope()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                // Lanzar una corutina dentro de un scope seguro para la composición
                coroutineScope.launch {
                    val imageBytes = getImageBytesFromUri(uri, contentResolver)
                    onImageSelected(imageBytes)
                }
            } ?: run {
                onImageSelected(null)
            }
        }
    )

    return remember {
        GalleryManager(
            onLaunch = {
                galleryLauncher.launch("image/*")
            }
        )
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GalleryManager  constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
