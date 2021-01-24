package zisis.aristofanis.animehouse.presentation.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AnimeListViewModel(
) : BaseViewModel<AnimeListContract.ViewState, AnimeListContract.Event, AnimeListContract.ViewEffects>(
    AnimeListContract.ViewState()
) {

    override suspend fun consumeIntentAction(intentAction: AnimeListContract.Event) {
        when (intentAction) {
            is AnimeListContract.Event.LaunchAnimeListIntentAction -> {
                launchAnimeListFlow(intentAction)
            }
            is AnimeListContract.Event.ListItemClickIntentAction -> {
                showClickedAnimeDetails(intentAction.anime)
            }
        }
    }

    private fun showClickedAnimeDetails(anime: Anime) {
        consumeSideEffect { AnimeListContract.ViewEffects.ShowAnimeState(anime) }
    }

    private fun launchAnimeListFlow(userIntents: AnimeListContract.Event.LaunchAnimeListIntentAction) {
        viewModelScope.launch {
            userIntents.animeListUseCase(userIntents.filterToQuery()).collect {
                emitState {
                    AnimeListContract.ViewState(it, userIntents.filter)
                }
            }
        }
    }

}


