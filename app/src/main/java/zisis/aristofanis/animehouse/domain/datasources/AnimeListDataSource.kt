package zisis.aristofanis.animehouse.domain.datasources

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.QueryData

interface AnimeListDataSource {

    suspend fun getAnimeList(params: AnimeListQuery): QueryData<AnimeListWithInfo>

}
