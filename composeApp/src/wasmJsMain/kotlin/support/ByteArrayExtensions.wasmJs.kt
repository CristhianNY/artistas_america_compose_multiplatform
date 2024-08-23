package support

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray?.toImageBitmap(): ImageBitmap? {
    val image = this?.let { Image.makeFromEncoded(it) }
    return image?.toComposeImageBitmap()
}

