package zisis.aristofanis.animehouse.presentation.utils

import zisis.aristofanis.animehouse.presentation.view_models.factory.AnimeListViewModelFactory

object InjectUtils {

    fun provideAnimeListViewModelFactory(): AnimeListViewModelFactory{
        return AnimeListViewModelFactory()
    }

}
