package zisis.aristofanis.animehouse.domain.models

data class AnimeListWithInfo(
     val page: PageInfo = PageInfo(),
     val animeList: List<Anime> = listOf()
)
