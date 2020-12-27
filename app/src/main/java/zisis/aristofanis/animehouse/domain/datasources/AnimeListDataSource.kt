package zisis.aristofanis.animehouse.domain.datasources

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState

interface AnimeListDataSource {

    suspend fun getAnimeList(params: AnimeListQuery): AnimeListState

}
