package zisis.aristofanis.animehouse.core.data

import com.apollographql.apollo.exception.ApolloException
import zisis.aristofanis.animehouse.core.domain.ApolloError
import zisis.aristofanis.animehouse.core.domain.GENERIC_ERROR_MESSAGE
import zisis.aristofanis.animehouse.core.domain.Result
import zisis.aristofanis.animehouse.core.domain.UnknownError


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
