package zisis.aristofanis.animehouse.data

import AnimeListQuery
import com.apollographql.apollo.coroutines.toDeferred
import zisis.aristofanis.animehouse.data.mappers.AnimeListWithInfoMappers
import zisis.aristofanis.animehouse.data.network.RestProvider
import zisis.aristofanis.animehouse.data.network.result
import zisis.aristofanis.animehouse.domain.datasources.AnimeListDataSource
import zisis.aristofanis.animehouse.domain.models.QueryData

class AnimeListWithInfoRepository : AnimeListDataSource {
    private val query =  AnimeListQuery.builder().build()

    override suspend fun getAnimeList(): QueryData =
        result {  AnimeListWithInfoMappers().transform(RestProvider.client.query(query).toDeferred().await().data())  }

}
