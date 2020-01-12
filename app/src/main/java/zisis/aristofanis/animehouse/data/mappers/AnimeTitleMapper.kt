package zisis.aristofanis.animehouse.data.mappers

import zisis.aristofanis.animehouse.domain.models.AnimeTitle

class AnimeTitleMapper {
    fun transform(response: AnimeListQuery.Title?): AnimeTitle {
        response?.let {
            return AnimeTitle(
                native = it.native_() ?: "",
                romaji = it.romaji() ?: ""
            )
        } ?: return AnimeTitle()
    }
}
