package support

import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.UIKit.*
import platform.darwin.NSObject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// Clase que actúa como wrapper para el ImagePicker en iOS
actual class ImagePicker {

    private lateinit var continuation: Continuation<ByteArray?>

    // Instancia del delegado que maneja la lógica de presentación y captura de imágenes
    private val pickerDelegate = IOSImagePickerDelegate()

    // Función suspendida que lanza el picker y retorna los bytes de la imagen seleccionada
    actual suspend fun pickImage(): ByteArray? {
        // Presenta el picker
        pickerDelegate.presentPicker()
        // Espera el resultado de la selección de la imagen
        return pickerDelegate.pickImage()
    }

    // Clase interna que maneja la lógica del UIImagePickerController y sus delegados
    private class IOSImagePickerDelegate : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

        private lateinit var continuation: Continuation<ByteArray?>

        // Función suspendida que permite suspender la ejecución hasta que el usuario seleccione una imagen o cancele
        suspend fun pickImage(): ByteArray? {
            return suspendCancellableCoroutine { continuation ->
                this.continuation = continuation
                // La presentación del picker se realiza antes de esta línea
            }
        }

        // Método para presentar el UIImagePickerController
        fun presentPicker() {
            val picker = UIImagePickerController().apply {
                delegate = this@IOSImagePickerDelegate // Asignamos el delegado
                sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            }

            // Obtener el rootViewController para presentar el picker
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            rootViewController?.presentViewController(picker, animated = true, completion = null)
        }

        // Método delegado de UIImagePickerController que se llama cuando se selecciona una imagen
        @Suppress("unused")
        fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>?
        ) {
            picker.dismissViewControllerAnimated(true, null)
            val image = didFinishPickingMediaWithInfo?.get(UIImagePickerControllerOriginalImage) as? UIImage
            if (image != null) {
                val data: NSData? = UIImageJPEGRepresentation(image, 0.8) ?: UIImagePNGRepresentation(image)
                if (data != null) {
                    val bytes = data.toString().toByteArray()
                    continuation.resume(bytes)
                } else {
                    continuation.resumeWithException(IllegalStateException("Failed to convert image to data"))
                }
            } else {
                continuation.resumeWithException(IllegalStateException("No image found"))
            }
        }

        // Método delegado de UIImagePickerController que se llama cuando se cancela la selección
        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            picker.dismissViewControllerAnimated(true, null)
            continuation.resume(null)
        }
    }
}
