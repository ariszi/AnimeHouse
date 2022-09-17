package zisis.aristofanis.animehouse.features.anime_list.domain.usecases

import zisis.aristofanis.animehouse.core.domain.Result
import zisis.aristofanis.animehouse.features.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.features.anime_list.domain.repository.AnimeListRepositoryContract
import zisis.aristofanis.animehouse.type.MediaSort
import zisis.aristofanis.animehouse.type.MediaStatus
import javax.inject.Inject

class AnimeListUseCase @Inject constructor(private val animeListRepositoryContract: AnimeListRepositoryContract) {

    suspend fun invoke(params: AnimeFilter = AnimeFilter()): Result<AnimeListWithInfo> {
        return animeListRepositoryContract.getAnimeList(params)
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
