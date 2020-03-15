package zisis.aristofanis.animehouse.data.network

import zisis.aristofanis.animehouse.domain.models.QueryData
import com.apollographql.apollo.api.Error as ApolloError

inline fun <Domain> result(codeBlock: () -> Domain): QueryData<Domain> =
    try {
        QueryData.Success(codeBlock())
    } catch (e: Exception) {
        QueryData.Error(ApolloError(e.message, listOf(), mapOf()))
    }

