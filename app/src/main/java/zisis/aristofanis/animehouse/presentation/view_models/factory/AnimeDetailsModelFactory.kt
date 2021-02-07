package zisis.aristofanis.animehouse.presentation.view_models.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.view_models.AnimeDetailsViewModel

class AnimeDetailsModelFactory(val anime: Anime) : ViewModelProvider.Factory {

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnimeDetailsViewModel(anime) as T
    }
}
