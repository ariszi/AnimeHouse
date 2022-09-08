package zisis.aristofanis.animehouse.anime_list.domain.repository

import zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.anime_list.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.core.domain.Result


interface AnimeListRepositoryContract {
    suspend fun getAnimeList(params: AnimeListUseCase.AnimeFilter): Result<AnimeListWithInfo>

}
