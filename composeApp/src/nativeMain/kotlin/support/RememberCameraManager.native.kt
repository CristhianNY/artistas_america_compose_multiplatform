package support

// CameraManager.ios.kt (iosMain)
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
actual fun rememberCameraManager(onImageCaptured: (ByteArray?) -> Unit): CameraManager {
    val coroutineScope = rememberCoroutineScope()
    val cameraDelegate = remember {
        IOSCameraDelegate { imageBytes ->
            coroutineScope.launch {
                onImageCaptured(imageBytes)
            }
        }.freeze()
    }
    return remember {
        CameraManager {
            cameraDelegate.presentCamera()
        }
    }
}

actual class CameraManager(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

private class IOSCameraDelegate(
    private val onImageCaptured: (ByteArray?) -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    fun presentCamera() {
        val picker = UIImagePickerController().apply {
            delegate = this@IOSCameraDelegate
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
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
        onImageCaptured(imageBytes)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true, null)
        onImageCaptured(null)
    }
}
