package zisis.aristofanis.animehouse.anime_list.presentation.view_models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.anime_list.presentation.core.BaseViewModel
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeContract

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AnimeViewModel :
    BaseViewModel<AnimeContract.ViewState, AnimeContract.Event, AnimeContract.ViewEffects>(AnimeContract.ViewState()) {

    init {
        consumeSideEffect { AnimeContract.ViewEffects.NavigateToAnimeList }
    }

    override suspend fun consumeIntentAction(intentAction: AnimeContract.Event) {
        when (intentAction) {
            is AnimeContract.Event.AnimeDetails -> handleNavigationToAnimeDetails(intentAction)
        }
    }

    private fun handleNavigationToAnimeDetails(intentAction: AnimeContract.Event.AnimeDetails) {
        consumeSideEffect { AnimeContract.ViewEffects.NavigateToAnimeDetails(intentAction.anime) }
    }

}
