package zisis.aristofanis.animehouse.presentation.di

import dagger.assisted.AssistedFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.view_models.AnimeDetailsViewModel

@AssistedFactory
interface ViewModelFactory {
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun create(params: Anime): AnimeDetailsViewModel
}
