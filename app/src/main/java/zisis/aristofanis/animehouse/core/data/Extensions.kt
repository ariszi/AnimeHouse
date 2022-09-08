package zisis.aristofanis.animehouse.core.data

import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.coroutines.await
import zisis.aristofanis.animehouse.core.domain.MalformedContractError
import zisis.aristofanis.animehouse.core.domain.Result

suspend fun <R> ApolloQueryCall<R>.toResult(): Result<R> {
    val response = await()
    return runCatching {
        Result.Success(response.data ?: throw IllegalStateException("Response data is null"))
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
