package zisis.aristofanis.animehouse.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.datasources.AnimeListDataSource
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState

class AnimeListUseCase(private val animeListDataSource: AnimeListDataSource) :
    UseCase<AnimeListState, AnimeListQuery>() {

    override suspend fun call(params: AnimeListQuery): Flow<AnimeListState> {
        return flow {
            emit(AnimeListState.LoadingState(true))
            emit(animeListDataSource.getAnimeList(params))
            emit(AnimeListState.LoadingState(false))

        }
    }

}
