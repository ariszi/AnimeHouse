package zisis.aristofanis.animehouse.presentation.state_management

import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase

sealed class AnimeListContract {

    sealed class AnimeListEvent : Action {

        data class ListItemClickIntentEvent(val anime: Anime) : AnimeListEvent()
        data class LaunchAnimeListEvent(val animeListUseCase: AnimeListUseCase) : AnimeListEvent()

    }

    sealed class AnimeListState : State {
        object InitState: AnimeListState()
        data class LoadingState(val loading: Boolean) : AnimeListState()
        data class ShowAnimeListState(val animeList: AnimeListWithInfo) : AnimeListState()
        data class ErrorState(val errorText: String) : AnimeListState()
    }


    sealed class AnimeListSideEffects : SideEffects {
        data class ShowAnimeState(val result: Anime) : AnimeListSideEffects()
    }
}
