package zisis.aristofanis.animehouse.features.anime_list.data.raw

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.core.utils.EMPTY
import zisis.aristofanis.animehouse.features.anime_list.domain.models.*

data class AnimeListRaw(
    val page: AnimeListQuery.PageInfo? = null,
    val animeList: MutableList<AnimeListQuery.Medium>? = null
) {

    fun toAnimeListWithInfo(): AnimeListWithInfo =
        AnimeListWithInfo(
            page = page.toPageInfo(),
            animeList = animeList?.map { it.toAnime() } ?: listOf()
        )

    private fun AnimeListQuery.PageInfo?.toPageInfo(): PageInfo {
        return this?.let {
            return PageInfo(
                currentPage = it.currentPage() ?: 0,
                hasNextPage = it.hasNextPage() ?: false,
                lastPage = it.lastPage() ?: 0,
                perPage = it.perPage() ?: 0,
                total = it.total() ?: 0
            )
        } ?: return PageInfo()
    }

    private fun toAnimeTitle(response: AnimeListQuery.Title?): AnimeTitle {
        response?.let {
            return AnimeTitle(
                english = it.english() ?: EMPTY,
                romaji = it.romaji() ?: EMPTY
            )
        } ?: return AnimeTitle()
    }


    private fun returnStatus(status: String): Status =
        when (status) {
            "FINISHED" -> Status.FINISHED
            "RELEASING" -> Status.RELEASING
            "NOT_YET_RELEASED" -> Status.NOT_YET_RELEASED
            "CANCELLED" -> Status.CANCELLED
            else -> Status.UNKNOWN
        }

    private fun AnimeListQuery.Medium.toAnime(): Anime {
        this.let {
            return Anime(
                description = it.description() ?: EMPTY,
                episodes = it.episodes() ?: 0,
                genres = it.genres(),
                popularity = it.popularity().toString(),
                status = returnStatus(it.status().toString()),
                image = it.coverImage()?.extraLarge() ?: EMPTY,
                title = toAnimeTitle(it.title())
            )
        }
    }
}
 fun AnimeListQuery.Data.toAnimeList(): AnimeListWithInfo {
    return AnimeListRaw(
        page = this.Page()?.pageInfo(),
        animeList = this.Page()?.media()
    ).toAnimeListWithInfo()
}
