package navigation

import kotlinx.browser.window

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UrlHandler {
    private val baseUrl: String = window.location.origin

    actual fun pushUrl(path: String) {
        window.history.pushState(null, "", "$baseUrl/$path")
    }

    actual fun replaceUrl(path: String) {
        window.history.replaceState(null, "", "$baseUrl/$path")
    }

    actual fun getPath(): String {
        return window.location.pathname.removePrefix("/")
    }
}

