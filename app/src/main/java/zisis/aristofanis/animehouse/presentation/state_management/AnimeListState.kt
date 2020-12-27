package zisis.aristofanis.animehouse.presentation.state_management

import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase

class AnimeListContract {

    sealed class AnimeListAction : UserAction {

        data class ListItemClickIntentAction(val anime: AnimeListState.ShowAnimeState) : AnimeListAction()

        data class LaunchAnimeListAction(val animeListUseCase: AnimeListUseCase) : AnimeListAction()

    }
    sealed class AnimeListState : State {

        data class ShowAnimeListState(val animeList: AnimeListWithInfo) : AnimeListState()
        data class ShowAnimeState(val result: Anime) : AnimeListState()
        data class ErrorState(val errorText: String) : AnimeListState()
        data class LoadingState(val loading: Boolean) : AnimeListState()
    }
}


