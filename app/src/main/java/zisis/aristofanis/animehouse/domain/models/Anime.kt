package zisis.aristofanis.animehouse.domain.models

import zisis.aristofanis.animehouse.domain.models.Status.UNKNOWN
import zisis.aristofanis.animehouse.domain.utils.EMPTY
import zisis.aristofanis.animehouse.domain.utils.TWO_UPPER_DASHES

class Anime(
    val description: String = EMPTY,
    val episodes: Int = 0,
    val genres: List<String>? = null,
    val id: Int = 0,
    val status: Status = UNKNOWN,
    val averageScore: String = TWO_UPPER_DASHES,
    val popularity: String = TWO_UPPER_DASHES,
    val image: String = EMPTY,
    val trending: String = EMPTY,
    val title: AnimeTitle = AnimeTitle()
)

enum class Status {
    RELEASING,
    FINISHED,
    NOT_YET_RELEASED,
    CANCELLED,
    UNKNOWN
}
