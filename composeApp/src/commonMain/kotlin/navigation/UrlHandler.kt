package navigation

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class UrlHandler() {
    fun pushUrl(path: String)
    fun replaceUrl(path: String)
    fun getPath(): String
}