package zisis.aristofanis.animehouse.features.anime_list.domain.models

import zisis.aristofanis.animehouse.core.utils.EMPTY
import zisis.aristofanis.animehouse.core.utils.TWO_UPPER_DASHES
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Status.UNKNOWN
import java.io.Serializable

data class Anime(
    val description: String = EMPTY,
    val episodes: Int = 0,
    val genres: List<String?>? = null,
    val id: Int = 0,
    val status: Status = UNKNOWN,
    val averageScore: String = TWO_UPPER_DASHES,
    val popularity: String = TWO_UPPER_DASHES,
    val image: String = EMPTY,
    val trending: String = EMPTY,
    val title: AnimeTitle = AnimeTitle()
): Serializable

enum class Status {
    RELEASING,
    FINISHED,
    NOT_YET_RELEASED,
    CANCELLED,
    UNKNOWN
}
