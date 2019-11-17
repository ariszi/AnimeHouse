package zisis.aristofanis.animehouse.domain

data class AnimeListWithInfo(
     val page: PageInfo = PageInfo(),
     val animeList: List<Anime> = listOf()
)
