package zisis.aristofanis.animehouse.data.api.network

import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.domain.utils.EMPTY
import com.apollographql.apollo.api.Error as ApolloError

inline fun <Domain> result(codeBlock: () -> Domain): QueryData<Domain> =
    try {
        QueryData.Success(codeBlock())
    } catch (e: Exception) {
        QueryData.Error(ApolloError(e.localizedMessage?: EMPTY, listOf(), mapOf()))
    }

