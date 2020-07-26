package zisis.aristofanis.animehouse.presentation.utils

import zisis.aristofanis.animehouse.data.AnimeListWithInfoRepository
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.view_models.factory.AnimeListViewModelFactory

object InjectUtils {

    fun provideAnimeListViewModelFactory(): AnimeListViewModelFactory{
        val animeListUseCase = AnimeListUseCase(AnimeListWithInfoRepository())
        return AnimeListViewModelFactory(animeListUseCase)
    }

}
