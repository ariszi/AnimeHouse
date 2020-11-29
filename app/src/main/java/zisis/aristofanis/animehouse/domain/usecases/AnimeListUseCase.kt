package zisis.aristofanis.animehouse.domain.usecases

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.datasources.AnimeListDataSource
import zisis.aristofanis.animehouse.presentation.state_management.State

class AnimeListUseCase(private val animeListDataSource: AnimeListDataSource) : UseCase<State, AnimeListQuery>() {

    override suspend fun call(params: AnimeListQuery): State =
        animeListDataSource.getAnimeList(params)

}
