@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package support

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

// En el commonMain
actual object SettingsManager {
    actual val settings: Settings by lazy {
        StorageSettings()
    }
}