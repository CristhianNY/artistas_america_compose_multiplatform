package support

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap


actual fun ByteArray?.toImageBitmap(): ImageBitmap? {
    if (this == null || this.isEmpty()) {
        println("ByteArray is null or empty")
        return null
    }

    println("ByteArray size: ${this.size}")

    val bitmap = try {
        BitmapFactory.decodeByteArray(this, 0, this.size)
    } catch (e: Exception) {
        println("Exception during decoding: ${e.message}")
        null
    }

    return bitmap?.asImageBitmap()
}