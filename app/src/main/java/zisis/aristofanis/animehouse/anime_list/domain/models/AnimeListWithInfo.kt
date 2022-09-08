package zisis.aristofanis.animehouse.anime_list.domain.models

data class AnimeListWithInfo(
    val page: PageInfo,
    val animeList: List<Anime>
)
