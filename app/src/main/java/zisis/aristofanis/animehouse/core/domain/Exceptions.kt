package zisis.aristofanis.animehouse.core.domain

import com.apollographql.apollo3.exception.ApolloException


fun Throwable.toError(): Result.Error {
    return when (this) {
        is MappableError -> {
            this.toError()
        }
        is ApolloException -> {
            ApolloError(message = this.message ?: GENERIC_ERROR_MESSAGE, cause = this.cause)
        }
        else -> {
            UnknownError(this)
        }
    }
}

interface MappableError {

    fun toError(): Result.Error

}
