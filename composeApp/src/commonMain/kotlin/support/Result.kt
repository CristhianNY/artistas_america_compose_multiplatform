package support

data class Result<T>(var status: Status, var data: T? = null, val message: String? = "") {

    sealed class Status {
        object SUCCESS : Status()
        class ERROR(val throwable: Throwable?, val code: Int, val errorBody: String = "") : Status()
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(
                status = Status.SUCCESS,
                data = data,
                message = null
            )
        }

        fun <T> error(
            exception: Exception,
            code: Int,
            errorBody: String = "",
            data: T? = null
        ): Result<T> {
            return Result(
                status = Status.ERROR(exception, code, errorBody),
                data = data,
                message = exception.message
            )
        }
    }
}