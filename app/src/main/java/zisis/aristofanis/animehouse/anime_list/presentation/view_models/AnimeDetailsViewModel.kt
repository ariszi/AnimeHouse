package zisis.aristofanis.animehouse.anime_list.presentation.view_models

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.presentation.core.BaseViewModel
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeData.AnimeDetails
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeDetailsContract

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AnimeDetailsViewModel @AssistedInject constructor(@Assisted val anime: Anime) :
    BaseViewModel<AnimeDetailsContract.ViewState, AnimeDetailsContract.Event, AnimeDetailsContract.ViewEffects>(
        AnimeDetailsContract.ViewState(AnimeDetails(anime))
    ) {

    override suspend fun consumeIntentAction(intentAction: AnimeDetailsContract.Event) {
        when(intentAction){
        }

    }
}


inline fun <reified T : ViewModel> Fragment.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
            viewModelProducer(handle) as T
    }
}
