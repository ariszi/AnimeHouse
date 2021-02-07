package zisis.aristofanis.animehouse.domain.datasources

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList

interface AnimeListRepositoryContract {

    suspend fun getAnimeList(params: AnimeListQuery): AnimeList

}
