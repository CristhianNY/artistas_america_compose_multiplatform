package support

import androidx.compose.runtime.Composable

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager
