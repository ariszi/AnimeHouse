package zisis.aristofanis.animehouse.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.datasources.AnimeListRepositoryContract
import zisis.aristofanis.animehouse.presentation.state_management.AnimeList

class AnimeListUseCase(private val animeListRepositoryContract: AnimeListRepositoryContract) :
    UseCase<AnimeList, AnimeListQuery>() {

    override suspend fun call(params: AnimeListQuery): Flow<AnimeList> {
        return flow {
            emit(AnimeList.Loading(true))
            emit(animeListRepositoryContract.getAnimeList(params))
            emit(AnimeList.Loading(false))
        }
    }

}
