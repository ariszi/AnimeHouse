package zisis.aristofanis.animehouse.features.anime_list.domain.models

data class AnimeListWithInfo(
    val page: PageInfo,
    val animeList: List<Anime>
)
