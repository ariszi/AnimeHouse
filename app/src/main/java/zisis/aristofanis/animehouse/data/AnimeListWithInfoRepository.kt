package zisis.aristofanis.animehouse.data

import zisis.aristofanis.animehouse.domain.AnimeListDataSource
import zisis.aristofanis.animehouse.domain.QueryData

class AnimeListWithInfoRepository(private val animeListClient: AnimeListClient) : AnimeListDataSource {

    override suspend fun getAnimeList(): QueryData {
        return animeListClient.getAnimeList()
    }

}
