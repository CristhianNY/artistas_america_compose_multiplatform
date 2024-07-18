package support

sealed class ResultDomain<out T> {
    data class Success<out T>(val data: T?) : ResultDomain<T>()
    data class Error(val error: ErrorDomain) : ResultDomain<Nothing>()

    val extractData: T?
        get() = when (this) {
            is Success -> data
            is Error -> null
        }
}