package support

import androidx.compose.runtime.Composable
import kotlinx.browser.window

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "UNREACHABLE_CODE")
actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {

    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.GALLERY -> callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            else -> callback.onPermissionStatus(permission, PermissionStatus.DENIED)
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> false
            PermissionType.GALLERY -> true
            else -> false
        }
    }

    @Composable
    override fun launchSettings() {
        window.open("about:preferences", "_blank")
    }
}
