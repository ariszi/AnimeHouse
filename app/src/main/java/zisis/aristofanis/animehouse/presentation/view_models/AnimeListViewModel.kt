package zisis.aristofanis.animehouse.presentation.view_models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

    private fun showClickedAnimeDetails(anime: Anime): Flow<AnimeListState> {
        return flow {
            emit(ShowAnimeState(anime))
        }
    }

    override fun handleIntentAction(intentAction: AnimeListAction) {
        setState {
            when (intentAction) {
                is LaunchAnimeListAction -> {
                    launchAnimeListFlow(intentAction)
                }
                is ListItemClickIntentAction -> {
                    showClickedAnimeDetails(intentAction.anime.result)
                }
            }
        }
    }

    private fun launchAnimeListFlow(userIntents: LaunchAnimeListAction): Flow<AnimeListState> {
        val query: AnimeListQuery = AnimeListQuery.builder().sort(listOf(MediaSort.TRENDING_DESC)).build()
        return flow {
            emit(LoadingState(true))
            emit(userIntents.animeListUseCase(query))
            emit(LoadingState(false))
        }
    }

}


