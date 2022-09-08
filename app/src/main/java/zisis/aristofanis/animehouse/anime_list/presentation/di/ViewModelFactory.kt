package zisis.aristofanis.animehouse.anime_list.presentation.di

import dagger.assisted.AssistedFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.presentation.view_models.AnimeDetailsViewModel

@AssistedFactory
interface ViewModelFactory {
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun create(params: Anime): AnimeDetailsViewModel
}
