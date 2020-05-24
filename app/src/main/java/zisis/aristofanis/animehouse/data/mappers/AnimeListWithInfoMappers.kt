package zisis.aristofanis.animehouse.data.mappers

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.AnimeTitle
import zisis.aristofanis.animehouse.domain.models.PageInfo
import zisis.aristofanis.animehouse.domain.models.Status
import zisis.aristofanis.animehouse.domain.models.Status.CANCELLED
import zisis.aristofanis.animehouse.domain.models.Status.FINISHED
import zisis.aristofanis.animehouse.domain.models.Status.NOT_YET_RELEASED
import zisis.aristofanis.animehouse.domain.models.Status.RELEASING
import zisis.aristofanis.animehouse.domain.models.Status.UNKNOWN
import zisis.aristofanis.animehouse.domain.utils.EMPTY

class AnimeListWithInfoMappers {

    fun transform(response: AnimeListQuery.Data?): AnimeListWithInfo = response?.let {
        AnimeListWithInfo(
            page = transform(it.Page()?.pageInfo()),
            animeList = animeList(it.Page()?.media() as MutableList<AnimeListQuery.Medium>)
        )
    } ?: AnimeListWithInfo()

    private fun transform(response: AnimeListQuery.Title?): AnimeTitle {
        response?.let {
            return AnimeTitle(
                english = it.english() ?: EMPTY,
                romaji = it.romaji() ?: EMPTY
            )
        } ?: return AnimeTitle()
    }

    private fun transform(response: AnimeListQuery.Medium?): Anime {
        response.let {
            return Anime(
                description = it?.description() ?: EMPTY,
                episodes = it?.episodes() ?: 0,
                genres = it?.genres(),
                status = returnStatus(it?.status().toString()),
                image = it?.coverImage()?.extraLarge()?: EMPTY,
                title = transform(it?.title())
            )
        }
    }

    private fun returnStatus(status: String): Status =
        when (status) {
            "FINISHED" -> FINISHED
            "RELEASING" -> RELEASING
            "NOT_YET_RELEASED" -> NOT_YET_RELEASED
            "CANCELLED" -> CANCELLED
            else -> UNKNOWN
        }

    private fun transform(response: AnimeListQuery.PageInfo?): PageInfo {
        response?.let {
            return PageInfo(
                currentPage = it.currentPage() ?: 0,
                hasNextPage = it.hasNextPage() ?: false,
                lastPage = it.lastPage() ?: 0,
                perPage = it.perPage() ?: 0,
                total = it.total() ?: 0
            )
        } ?: return PageInfo()
    }

    fun animeList(animeListRaw: MutableList<AnimeListQuery.Medium>): List<Anime> {
        val animeList: MutableList<Anime> = mutableListOf()
        for (anime in animeListRaw) {
            animeList.add(transform(anime))
        }
        return animeList
    }

}
