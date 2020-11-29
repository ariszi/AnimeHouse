package zisis.aristofanis.animehouse.domain.datasources

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.presentation.state_management.State

interface AnimeListDataSource {

    suspend fun getAnimeList(params: AnimeListQuery): State

}
