package support

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ImagePicker {
    suspend fun pickImage(): ByteArray?
}