package zisis.aristofanis.animehouse.domain

import zisis.aristofanis.animehouse.domain.models.QueryData

class AnimeListUseCase(private val animeListDataSource: AnimeListDataSource) {

   suspend fun getAnimeListWithInfo(): QueryData {
        return animeListDataSource.getAnimeList()
    }

}
