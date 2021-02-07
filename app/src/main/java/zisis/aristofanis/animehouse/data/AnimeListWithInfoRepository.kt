package zisis.aristofanis.animehouse.data

import com.apollographql.apollo.coroutines.toDeferred
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.data.mappers.AnimeListWithInfoMappers
import zisis.aristofanis.animehouse.data.network.RestProvider
import zisis.aristofanis.animehouse.data.network.result
import zisis.aristofanis.animehouse.domain.datasources.AnimeListRepositoryContract
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList

class AnimeListWithInfoRepository : AnimeListRepositoryContract {

    override suspend fun getAnimeList(params: AnimeListQuery): AnimeList {
        val result = result {
            AnimeListWithInfoMappers().transform(
                RestProvider.client.query(params).toDeferred().await().data
            )
        }

      return  when (result) {
            is QueryData.Success -> AnimeList.AnimeListItems(result.data)
            is QueryData.Error -> AnimeList.ListError(result.errorMessage.message)
        }
    }
}
