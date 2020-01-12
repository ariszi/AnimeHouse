package zisis.aristofanis.animehouse.data.mappers

import zisis.aristofanis.animehouse.domain.models.Anime

class AnimeMapper {
    fun transform(response: AnimeListQuery.Medium?): Anime {
        response.let {
            return Anime(
                description = it?.description() ?: "",
                episodes = it?.episodes() ?: 0,
                genres = it?.genres(),
                status = it?.status().toString(),
                title = AnimeTitleMapper().transform(it?.title())
            )
        }
    }
}
