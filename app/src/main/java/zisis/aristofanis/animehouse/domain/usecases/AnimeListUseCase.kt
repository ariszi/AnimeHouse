package zisis.aristofanis.animehouse.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zisis.aristofanis.animehouse.domain.datasources.AnimeListRepositoryContract
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList
import zisis.aristofanis.animehouse.type.MediaSort
import zisis.aristofanis.animehouse.type.MediaStatus
import javax.inject.Inject

class AnimeListUseCase @Inject constructor(private val animeListRepositoryContract: AnimeListRepositoryContract) :
    UseCase<AnimeList, AnimeListUseCase.AnimeFilter>() {

    override suspend fun call(params: AnimeFilter): Flow<AnimeList> {
        return flow {
            emit(AnimeList.Loading(true))
            emit(animeListRepositoryContract.getAnimeList(params))
            emit(AnimeList.Loading(false))
        }
    }

    data class AnimeFilter(
        val id: Int? = null,
        val page: Int? = null,
        val perPage: Int? = null,
        val search: String? = null,
        val genre: String? = null,
        val status: MediaStatus? = null,
        val sort: List<MediaSort>? = listOf(MediaSort.POPULARITY_DESC)
    )

}
