package zisis.aristofanis.animehouse.domain.datasources

import zisis.aristofanis.animehouse.domain.models.QueryData

interface AnimeListDataSource {

    suspend fun getAnimeList(): QueryData

}
