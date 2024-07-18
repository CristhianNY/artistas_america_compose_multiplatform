package support

suspend fun <T, O> baseResponseErrorHandler(
    mapper: ErrorMapper,
    response: Result<T>,
    mapAction: suspend (T) -> ResultDomain<O>
): ResultDomain<O> = when (val statusResponse = response.status) {
    is Result.Status.SUCCESS -> response.data?.let { mapAction(it) }
        ?: ResultDomain.Error(mapper.genericError())
    is Result.Status.ERROR -> mapper.customError(statusResponse.code, statusResponse.errorBody)
}