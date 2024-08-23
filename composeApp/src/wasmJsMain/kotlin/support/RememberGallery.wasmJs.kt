package support

// GalleryManager.wasm.kt (wasmJsMain)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.browser.document
import org.khronos.webgl.get
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader

@Composable
actual fun rememberGalleryManager(onImageSelected: (ByteArray?) -> Unit): GalleryManager {
    // Create an invisible input element to select files (images)
    val inputRef = remember { document.createElement("input") as HTMLInputElement }
    inputRef.type = "file"
    inputRef.accept = "image/*"
    inputRef.style.display = "none" // Make it invisible

    DisposableEffect(Unit) {
        // Cleanup when the Composable is destroyed
        onDispose {
            inputRef.remove()
        }
    }

    // Set up the onChange event of the input
    LaunchedEffect(inputRef) {
        inputRef.onchange = {
            val file = inputRef.files?.item(0)
            file?.let {
                val reader = FileReader()
                reader.onloadend = { _ ->
                    val arrayBuffer = reader.result
                    if (arrayBuffer is org.khronos.webgl.ArrayBuffer) {
                        val byteArray = arrayBufferToByteArray(arrayBuffer)
                        onImageSelected(byteArray)
                    } else {
                        onImageSelected(null)
                    }
                }
                reader.readAsArrayBuffer(file)
            }
        }
    }

    return remember {
        GalleryManager(
            onLaunch = {
                inputRef.click() // Simulate the click to open the file selection dialog
            }
        )
    }
}

actual class GalleryManager constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}

// Helper function to convert ArrayBuffer to ByteArray
fun arrayBufferToByteArray(arrayBuffer: org.khronos.webgl.ArrayBuffer): ByteArray {
    val view = org.khronos.webgl.Int8Array(arrayBuffer)
    return ByteArray(view.length) { view[it] }
}
