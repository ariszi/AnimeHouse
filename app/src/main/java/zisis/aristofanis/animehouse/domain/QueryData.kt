package zisis.aristofanis.animehouse.domain

import com.apollographql.apollo.api.Error as ApolloError

sealed class QueryData {

    data class Error(val errorMessage: ApolloError) : QueryData()

    data class Success<T>(
        val data: T) : QueryData()
}
