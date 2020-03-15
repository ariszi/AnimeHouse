package zisis.aristofanis.animehouse.data.network

import zisis.aristofanis.animehouse.domain.models.QueryData
import com.apollographql.apollo.api.Error as ApolloError

inline fun <ResponseRaw> result(codeBlock: () -> ResponseRaw): QueryData =
    try {
        QueryData.Success(codeBlock())
    } catch (e: Exception) {
        QueryData.Error(ApolloError(e.message, listOf(), mapOf()))
    }

