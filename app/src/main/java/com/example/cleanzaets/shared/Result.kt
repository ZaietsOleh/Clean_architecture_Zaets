package com.example.cleanzaets.shared

class Result<T, E> private constructor(
    private val success: ValueWrapper<T>? = null,
    private val error: ValueWrapper<E>? = null
){

    private class ValueWrapper<T>(val value: T)

    val isError = error != null

    val successResult: T
        get() = requireNotNull(success) {
            RESULT_WAS_WITHOUT_SUCCESS
        }.value

    val errorResult: E
        get() = requireNotNull(error) {
            RESULT_WAS_WITHOUT_ERROR
        }.value

    fun <R> mapSuccess(transformation: (T) -> R): Result<R, E> {
        return Result(
            success = success?.let { ValueWrapper(transformation(it.value)) },
            error = error
        )
    }

    fun <R> mapError(transformation: (E) -> R): Result<T, R> {
        return Result(
            success = success,
            error = error?.let { ValueWrapper(transformation(it.value)) }
        )
    }

    companion object {
        private const val RESULT_WAS_WITHOUT_SUCCESS = "Result was without  success"
        private const val RESULT_WAS_WITHOUT_ERROR = "Result was without  error"

        fun <T, E> success(entity: T): Result<T, E> {
            return Result(ValueWrapper(entity), null)
        }

        fun <T, E> error(error: E): Result<T, E> {
            return Result(null, ValueWrapper(error))
        }
    }
}