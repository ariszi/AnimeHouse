package zisis.aristofanis.animehouse.domain.usecases

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.datasources.AnimeListDataSource
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState

class AnimeListUseCase(private val animeListDataSource: AnimeListDataSource) : UseCase<AnimeListState, AnimeListQuery>() {

    override suspend fun call(params: AnimeListQuery): AnimeListState =
        animeListDataSource.getAnimeList(params)

}
