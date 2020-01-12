package zisis.aristofanis.animehouse.data

import AnimeListQuery
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.coroutines.toDeferred
import zisis.aristofanis.animehouse.data.mappers.AnimeListWithInfoMapper
import zisis.aristofanis.animehouse.domain.models.QueryData
import java.lang.Exception

class AnimeListClient(private val query: AnimeListQuery) {

    suspend fun getAnimeList(): QueryData {
        try {
            val response = RestProvider.client.query(query).toDeferred().await()
            if (response.hasErrors()) {
                return QueryData.Error(response.errors()[0])
            }
            return QueryData.Success(AnimeListWithInfoMapper().transform(response.data()))
        } catch (e: Exception) {
            return QueryData.Error(Error(e.message, listOf(), mapOf()))
        }
    }
}
