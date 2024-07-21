@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package support

import android.content.Context
import android.preference.PreferenceManager
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

object AndroidSettingsManager {
    private lateinit var _settings: Settings

    fun init(context: Context) {
        if (!::_settings.isInitialized) {
            _settings = SharedPreferencesSettings(PreferenceManager.getDefaultSharedPreferences(context))
        }
    }

    val settings: Settings
        get() = _settings
}

actual object SettingsManager {
    actual val settings: Settings
        get() = AndroidSettingsManager.settings
}