package zisis.aristofanis.animehouse.presentation.view_models

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.core.BaseViewModel
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeListContract
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class AnimeListViewModel @Inject constructor(private val animeListUseCase: AnimeListUseCase) :
    BaseViewModel<AnimeListContract.ViewState, AnimeListContract.Event, AnimeListContract.ViewEffects>(
        AnimeListContract.ViewState()
    ) {

    override suspend fun consumeIntentAction(intentAction: AnimeListContract.Event) {
        when (intentAction) {
            is AnimeListContract.Event.GetAnimeListWithFilter -> {
                launchAnimeListFlow(intentAction)
            }
            is AnimeListContract.Event.ListItemClickIntentAction -> {
                navigateToAnimeDetails(intentAction.anime)
            }
        }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        consumeSideEffect { AnimeListContract.ViewEffects.NavigateToAnimeDetails(anime) }
    }

    private fun launchAnimeListFlow(userIntents: AnimeListContract.Event.GetAnimeListWithFilter) {
        viewModelScope.launch {
            emitState { AnimeListContract.ViewState(AnimeList.Loading(true)) }
            val result = userIntents.getAnimeList(animeListUseCase)
            emitState { AnimeListContract.ViewState(result) }
            emitState { AnimeListContract.ViewState(AnimeList.Loading(false)) }
        }
    }
}




