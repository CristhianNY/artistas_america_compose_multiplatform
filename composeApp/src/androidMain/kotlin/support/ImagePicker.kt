package support

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import kotlinx.coroutines.suspendCancellableCoroutine
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

    fun handleImageResult(uri: Uri?) {
        if (uri != null) {
            val byteArray = uri.toString().toByteArray() // Simulación de conversión
            continuation?.invoke(byteArray)
        } else {
            continuation?.invoke(null)
        }
    }
}
