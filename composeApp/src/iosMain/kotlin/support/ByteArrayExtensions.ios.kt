package support

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray?.toImageBitmap(): ImageBitmap? {
    return this?.let { Image.makeFromEncoded(it).toComposeImageBitmap() }
}