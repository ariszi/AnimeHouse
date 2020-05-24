package zisis.aristofanis.animehouse.domain.usecases

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.datasources.AnimeListDataSource
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.QueryData

class AnimeListUseCase(private val animeListDataSource: AnimeListDataSource) : UseCase<AnimeListWithInfo, AnimeListQuery>() {

    override suspend fun call(params: AnimeListQuery): QueryData<AnimeListWithInfo> =
        animeListDataSource.getAnimeList(params)

}
