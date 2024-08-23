package support

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryManager(onImageSelected: (ByteArray?) -> Unit): GalleryManager

expect class GalleryManager {
    fun launch()
}