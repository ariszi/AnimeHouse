package zisis.aristofanis.animehouse.data

import com.apollographql.apollo.coroutines.toDeferred
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.data.mappers.AnimeListWithInfoMappers
import zisis.aristofanis.animehouse.data.network.RestProvider
import zisis.aristofanis.animehouse.data.network.result
import zisis.aristofanis.animehouse.domain.datasources.AnimeListDataSource
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.presentation.state_management.ErrorState
import zisis.aristofanis.animehouse.presentation.state_management.ShowAnimeListState
import zisis.aristofanis.animehouse.presentation.state_management.State

class AnimeListWithInfoRepository : AnimeListDataSource {

    override suspend fun getAnimeList(params: AnimeListQuery): State {
        val result = result {
            AnimeListWithInfoMappers().transform(
                RestProvider.client.query(params).toDeferred().await().data
            )
        }

      return  when (result) {
            is QueryData.Success -> ShowAnimeListState(result.data)
            is QueryData.Error -> ErrorState(result.errorMessage.message)
        }
    }
}
