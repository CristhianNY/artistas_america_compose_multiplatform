package support

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set


expect object SettingsManager {
    val settings: Settings
}

fun saveToken(token: String?) {
    SettingsManager.settings["auth_token"] = token
}

fun getToken(): String? {
    return SettingsManager.settings.getStringOrNull("auth_token")
}