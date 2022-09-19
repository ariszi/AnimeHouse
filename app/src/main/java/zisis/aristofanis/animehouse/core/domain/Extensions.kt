package zisis.aristofanis.animehouse.core.domain

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Operation
import kotlinx.coroutines.flow.firstOrNull


suspend fun <R : Operation.Data> ApolloCall<R>.toResult(): Result<R> {

    return runCatching {
        val response = this.toFlow().firstOrNull()
        Result.Success(response?.data ?: throw IllegalStateException("Response data is null"))
    }.fold(
        onSuccess = { it },
        onFailure = { throwable ->
            throwable.toError()
        }
    )
}

inline fun <T, R> Result<T>.validateMap(mapper: (T) -> R): Result<R> = try {
    map(mapper)
} catch (exception: NullPointerException) {
    MalformedContractError(exception)
}

inline fun <T> Result<T>.mapError(mapper: (Result.Error) -> Result.Error): Result<T> = when (this) {
    is Result.Success -> this
    is Result.Error -> mapper(this)
}

inline fun <T, R> Result<T>.map(mapper: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(mapper(data))
    is Result.Error -> this
}

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> = when (this) {
    is Result.Success -> apply { action(data) }
    is Result.Error -> this
}

inline fun <T> Result<T>.onError(action: (Result.Error) -> Unit): Result<T> = when (this) {
    is Result.Success -> this
    is Result.Error -> apply { action(this) }
}

fun <T> Result<T>.successOrNull(): T? = when (this) {
    is Result.Success -> data
    else -> null
}

fun <T> Result<T>.errorOrNull(): Result.Error? = when (this) {
    is Result.Success -> null
    is Result.Error -> this
}
