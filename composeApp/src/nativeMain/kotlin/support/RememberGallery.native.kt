package support

// GalleryManager.ios.kt (iosMain)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import kotlinx.coroutines.launch
import platform.Foundation.NSData
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePNGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.native.concurrent.freeze

@Composable
actual fun rememberGalleryManager(onImageSelected: (ByteArray?) -> Unit): GalleryManager {
    // Obtener un CoroutineScope para lanzar corutinas de manera segura
    val coroutineScope = rememberCoroutineScope()

    val galleryDelegate = remember {
        IOSGalleryDelegate { imageBytes ->
            coroutineScope.launch {
                onImageSelected(imageBytes)
            }
        }.freeze()
    }

    return remember {
        GalleryManager {
            galleryDelegate.presentGallery()
        }
    }
}

actual class GalleryManager  constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

private class IOSGalleryDelegate(
    private val onImageSelected: (ByteArray?) -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    fun presentGallery() {
        val picker = UIImagePickerController().apply {
            delegate = this@IOSGalleryDelegate
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            allowsEditing = true
        }

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.let { viewController ->
            dispatch_async(dispatch_get_main_queue()) {
                viewController.presentViewController(picker, animated = true, completion = null)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        picker.dismissViewControllerAnimated(true, null)
        val image = didFinishPickingMediaWithInfo[UIImagePickerControllerEditedImage] as? UIImage
            ?: didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
        val imageData: NSData? = image?.let {
            UIImageJPEGRepresentation(it, 0.8) ?: UIImagePNGRepresentation(it)
        }
        val imageBytes = imageData?.bytes?.readBytes(imageData.length.toInt())
        onImageSelected(imageBytes)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true, null)
        onImageSelected(null)
    }
}
