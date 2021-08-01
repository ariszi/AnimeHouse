package zisis.aristofanis.animehouse.domain.datasources

import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList

interface AnimeListRepositoryContract {
    suspend fun getAnimeList(params: AnimeListUseCase.AnimeFilter): AnimeList

}
