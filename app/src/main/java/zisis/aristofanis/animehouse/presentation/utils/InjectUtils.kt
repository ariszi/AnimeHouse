package zisis.aristofanis.animehouse.presentation.utils

import zisis.aristofanis.animehouse.presentation.view_models.factory.AnimeListViewModelFactory
import zisis.aristofanis.animehouse.presentation.view_models.factory.AnimeViewModelFactory

object InjectUtils {

    fun provideAnimeListViewModelFactory(): AnimeListViewModelFactory{
        return AnimeListViewModelFactory()
    }

    fun provideAnimeViewModelFactory(): AnimeViewModelFactory {
        return AnimeViewModelFactory()
    }

}
