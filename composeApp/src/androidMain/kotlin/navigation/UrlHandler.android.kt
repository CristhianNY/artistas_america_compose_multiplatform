// shared/src/androidMain/kotlin/navigation/UrlHandlerAndroid.kt
package navigation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import org.artistasamerica.artistas.ArtistaApplication

actual class UrlHandler {
    private val context: Context = ArtistaApplication.context

    actual fun pushUrl(path: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = path.toUri()
        }
        context.startActivity(intent)
    }

    actual fun replaceUrl(path: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = path.toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }

    actual fun getPath(): String {
        return ""
    }
}
