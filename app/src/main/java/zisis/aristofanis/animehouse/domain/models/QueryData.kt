package zisis.aristofanis.animehouse.domain.models

import com.apollographql.apollo.api.Error as ApolloError

sealed class QueryData<out T> {

    data class Error<T>(val errorMessage: ApolloError) : QueryData<T>()

    data class Success<T>(val data: T) : QueryData<T>()
}
