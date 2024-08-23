package support

import androidx.compose.runtime.Composable
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

// PermissionsManager.ios.kt

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {

    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                askCameraPermission(status, permission, callback)
            }
            PermissionType.GALLERY -> {
                val status = PHPhotoLibrary.authorizationStatus()
                askGalleryPermission(status, permission, callback)
            }
        }
    }

    private fun askCameraPermission(
        status: AVAuthorizationStatus,
        permission: PermissionType,
        callback: PermissionCallback
    ) {
        when (status) {
            AVAuthorizationStatusAuthorized -> callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            AVAuthorizationStatusNotDetermined -> AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                if (granted) {
                    callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                } else {
                    callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                }
            }
            else -> callback.onPermissionStatus(permission, PermissionStatus.DENIED)
        }
    }

    private fun askGalleryPermission(
        status: PHAuthorizationStatus,
        permission: PermissionType,
        callback: PermissionCallback
    ) {
        when (status) {
            PHAuthorizationStatusAuthorized -> callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            PHAuthorizationStatusNotDetermined -> PHPhotoLibrary.requestAuthorization { newStatus ->
                askGalleryPermission(newStatus, permission, callback)
            }
            else -> callback.onPermissionStatus(permission, PermissionStatus.DENIED)
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) == AVAuthorizationStatusAuthorized
            PermissionType.GALLERY -> PHPhotoLibrary.authorizationStatus() == PHAuthorizationStatusAuthorized
        }
    }

    @Composable
    override fun launchSettings() {
        UIApplication.sharedApplication.openURL(NSURL.URLWithString(UIApplicationOpenSettingsURLString)!!)
    }
}
