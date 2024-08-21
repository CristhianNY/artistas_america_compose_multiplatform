package support
import kotlinx.browser.document
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.js.JsAny

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ImagePicker {
    actual suspend fun pickImage(): ByteArray? {
        return suspendCancellableCoroutine { continuation ->
            val input = document.createElement("input") as HTMLInputElement
            input.type = "file"
            input.accept = "image/*"

            input.onchange = {
                val file = input.files?.item(0)
                if (file != null) {
                    val reader = FileReader()
                    reader.onload = {
                        val result: JsAny? = reader.result
                        if (result != null) {
                            val resultString = result.toString()
                            // En este caso resultString será una URL de datos (data URL)
                            val base64Data = resultString.substringAfter("base64,")
                            val bytes = base64Data.encodeToByteArray()
                            continuation.resume(bytes)
                        } else {
                            continuation.resumeWithException(IllegalStateException("Failed to read file"))
                        }
                    }
                    reader.onerror = {
                        continuation.resumeWithException(IllegalStateException("Failed to read file"))
                    }
                    reader.readAsDataURL(file) // or use readAsArrayBuffer(file) if you prefer raw bytes
                } else {
                    continuation.resume(null)
                }
            }

            input.click() // Simula el click para abrir el selector de archivos
        }
    }
}
