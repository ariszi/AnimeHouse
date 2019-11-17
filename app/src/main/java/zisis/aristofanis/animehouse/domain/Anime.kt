package zisis.aristofanis.animehouse.domain

class Anime(
    val description: String = "",
    val episodes: Int = 0,
    val genres: List<String>? = null,
    val id: Int = 0,
    val status: String = "",
    val title: AnimeTitle = AnimeTitle()
)
