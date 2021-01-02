package zisis.aristofanis.animehouse.presentation.view_models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListEvent
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListEvent.LaunchAnimeListEvent
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListEvent.ListItemClickIntentEvent
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListSideEffects
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListSideEffects.ShowAnimeState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState
import zisis.aristofanis.animehouse.type.MediaSort

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AnimeListViewModel(
    animeListUseCase: AnimeListUseCase
) : BaseViewModel<AnimeListState, AnimeListEvent, AnimeListSideEffects>(
    AnimeListState.InitState, LaunchAnimeListEvent(animeListUseCase)
) {

    override suspend fun reduceIntentAction(intentAction: AnimeListEvent) {
        when (intentAction) {
            is LaunchAnimeListEvent -> {
                launchAnimeListFlow(intentAction)
            }
            is ListItemClickIntentEvent -> {
                showClickedAnimeDetails(intentAction.anime)
            }
        }
    }

    private fun showClickedAnimeDetails(anime: Anime) {
        consumeSideEffect { ShowAnimeState(anime) }
    }

    private suspend fun launchAnimeListFlow(userIntents: LaunchAnimeListEvent) {
        val query: AnimeListQuery = AnimeListQuery.builder().sort(listOf(MediaSort.POPULARITY_DESC)).build()
        reduceToState { userIntents.animeListUseCase(query) }
    }

}


