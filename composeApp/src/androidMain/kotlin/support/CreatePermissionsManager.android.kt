package support

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}