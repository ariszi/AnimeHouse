package zisis.aristofanis.animehouse.presentation.view_models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListAction
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListAction.LaunchAnimeListAction
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListAction.ListItemClickIntentAction
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState.LoadingState
import zisis.aristofanis.animehouse.presentation.state_management.AnimeListContract.AnimeListState.ShowAnimeState
import zisis.aristofanis.animehouse.type.MediaSort

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AnimeListViewModel(
    private val animeListUseCase: AnimeListUseCase
) : BaseViewModel<AnimeListState, AnimeListAction>(LoadingState(true), LaunchAnimeListAction(animeListUseCase)) {

    override suspend fun handleIntentAction(intentAction: AnimeListAction) {
        when (intentAction) {
            is LaunchAnimeListAction -> {
                launchAnimeListFlow(intentAction)
            }
            is ListItemClickIntentAction -> {
                showClickedAnimeDetails(intentAction.anime.result)
            }
        }
    }

    private suspend fun showClickedAnimeDetails(anime: Anime) {
        setState {
            flow { emit(ShowAnimeState(anime)) }
        }
    }

    private suspend fun launchAnimeListFlow(userIntents: LaunchAnimeListAction) {
        val query: AnimeListQuery = AnimeListQuery.builder().sort(listOf(MediaSort.TRENDING_DESC)).build()
        setState {
            userIntents.animeListUseCase(query)
        }
    }

}


