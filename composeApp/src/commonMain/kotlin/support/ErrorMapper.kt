package support

abstract class ErrorMapper {

    abstract fun customError(code: Int?, errorBody: String): ResultDomain.Error

    abstract fun genericError(): ErrorDomain
}