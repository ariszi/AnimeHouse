package zisis.aristofanis.animehouse.presentation.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.state_management.IdleState
import zisis.aristofanis.animehouse.presentation.state_management.LaunchAnimeListAction
import zisis.aristofanis.animehouse.presentation.state_management.ListItemClickIntentAction
import zisis.aristofanis.animehouse.presentation.state_management.LoadingState
import zisis.aristofanis.animehouse.presentation.state_management.ShowAnimeState
import zisis.aristofanis.animehouse.presentation.state_management.State
import zisis.aristofanis.animehouse.presentation.state_management.UserIntents
import zisis.aristofanis.animehouse.type.MediaSort

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AnimeListViewModel : BaseViewModel() {

    private val _state = MutableStateFlow<State>(IdleState)

    val state: StateFlow<State>
        get() = _state

    val intentChannel = Channel<UserIntents>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            handleIntent()
        }
    }


    private suspend fun handleIntent() {
        intentChannel.consumeAsFlow().collect { intent ->
            reducer(intent)
        }
    }

    private suspend fun reducer(userIntents: UserIntents) {
        when (userIntents) {
            is LaunchAnimeListAction -> {
                launchAnimeListFlow(userIntents).collect { _state.value = it }
            }
            is ListItemClickIntentAction -> {
                showClickedAnimeDetails(userIntents.anime.result).collect { _state.value = it }
            }
        }
    }

    private fun showClickedAnimeDetails(anime: Anime): Flow<State> {
        return flow {
            emit(ShowAnimeState(anime))
        }
    }

    private fun launchAnimeListFlow(userIntents: LaunchAnimeListAction): Flow<State> {
        val query: AnimeListQuery = AnimeListQuery.builder().sort(listOf(MediaSort.TRENDING_DESC)).build()
        return flow {
            emit(LoadingState(true))
            emit(userIntents.animeListUseCase(query))
            emit(LoadingState(false))
        }
    }

}


