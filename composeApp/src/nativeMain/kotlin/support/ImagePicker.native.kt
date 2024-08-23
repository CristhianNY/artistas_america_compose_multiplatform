package support

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.UIKit.*
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class ImagePicker {

    private val pickerDelegate = IOSImagePickerDelegate()

    actual suspend fun pickImage(): ByteArray? {
        return pickerDelegate.pickImage()
    }

    private class IOSImagePickerDelegate : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

        private var continuation: Continuation<ByteArray?>? = null

        suspend fun pickImage(): ByteArray? {
            return suspendCancellableCoroutine { cont ->
                continuation = cont
                presentPicker()
            }
        }

        private fun presentPicker() {
            val picker = UIImagePickerController().apply {
                delegate = this@IOSImagePickerDelegate
                sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            }

            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            rootViewController?.let { viewController ->
                println("Presenting image picker")
                dispatch_async(dispatch_get_main_queue()) {
                    val presentedVC = viewController.presentedViewController
                    if (presentedVC != null) {
                        presentedVC.presentViewController(picker, animated = true, completion = null)
                    } else {
                        viewController.presentViewController(picker, animated = true, completion = null)
                    }
                }
            } ?: run {
                continuation?.resumeWithException(IllegalStateException("No root view controller available"))
            }
        }

        @OptIn(ExperimentalForeignApi::class)
        fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>?
        ) {
            println("imagePickerController called with media info")
            picker.dismissViewControllerAnimated(true, null)
            val image = didFinishPickingMediaWithInfo?.get(UIImagePickerControllerOriginalImage) as? UIImage
            if (image != null) {
                val data: NSData? = UIImageJPEGRepresentation(image, 0.8) ?: UIImagePNGRepresentation(image)
                if (data != null) {
                    val bytes = data.bytes?.readBytes(data.length.toInt())
                    continuation?.resume(bytes)
                } else {
                    continuation?.resumeWithException(IllegalStateException("Failed to convert image to bytes"))
                }
            } else {
                continuation?.resumeWithException(IllegalStateException("No image found"))
            }
            continuation = null // Limpiar la referencia para evitar fugas de memoria
        }

        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            println("imagePickerControllerDidCancel called")
            picker.dismissViewControllerAnimated(true, null)
            continuation?.resume(null)
            continuation = null // Limpiar la referencia para evitar fugas de memoria
        }
    }
}
