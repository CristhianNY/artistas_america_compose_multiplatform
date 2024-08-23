package support

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class ImagePicker {

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var continuation: ((ByteArray?) -> Unit)? = null
    private var continuationWithException: ((Throwable) -> Unit)? = null

    fun initLauncher(launcher: ActivityResultLauncher<String>) {
        this.pickImageLauncher = launcher
    }

    actual suspend fun pickImage(): ByteArray? {
        return suspendCancellableCoroutine { cont ->
            continuation = { cont.resume(it) }
            continuationWithException = { cont.resumeWithException(it) }
            try {
                pickImageLauncher.launch("image/*")
            } catch (e: Exception) {
                continuationWithException?.invoke(e)
            }
        }
    }

    fun Context.readBytesFromUri(uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            inputStream?.buffered()?.use { it.readBytes() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun handleImageResult(context: Context, uri: Uri?) {
        if (uri != null) {
            val byteArray = context.readBytesFromUri(uri)
            continuation?.invoke(byteArray)
        } else {
            continuation?.invoke(null)
        }
    }
}
