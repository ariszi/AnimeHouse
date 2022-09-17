package zisis.aristofanis.animehouse.features.anime_list.domain.repository

import zisis.aristofanis.animehouse.core.domain.Result
import zisis.aristofanis.animehouse.features.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.features.anime_list.domain.usecases.AnimeListUseCase


interface AnimeListRepositoryContract {
    suspend fun getAnimeList(params: AnimeListUseCase.AnimeFilter): Result<AnimeListWithInfo>

}
