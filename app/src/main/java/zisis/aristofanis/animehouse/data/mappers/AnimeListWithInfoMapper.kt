package zisis.aristofanis.animehouse.data.mappers

import zisis.aristofanis.animehouse.domain.Anime
import zisis.aristofanis.animehouse.domain.AnimeListWithInfo

class AnimeListWithInfoMapper {
    fun transform(response: AnimeListQuery.Data?): AnimeListWithInfo = response?.let {
        AnimeListWithInfo(
            page = PageMapper().transform(it.Page()?.pageInfo()),
            animeList = animeList(it.Page()?.media() as MutableList<AnimeListQuery.Medium>)
        )
    } ?: AnimeListWithInfo()

    fun animeList(animeListRaw: MutableList<AnimeListQuery.Medium>): List<Anime> {
        val animeList: MutableList<Anime> = mutableListOf()
        for (anime in animeListRaw) {
            animeList.add(AnimeMapper().transform(anime))
        }
        return animeList
    }
}
