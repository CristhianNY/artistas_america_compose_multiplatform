package support

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

// En el commonMain
actual object SettingsManager {
    actual val settings: Settings by lazy {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}