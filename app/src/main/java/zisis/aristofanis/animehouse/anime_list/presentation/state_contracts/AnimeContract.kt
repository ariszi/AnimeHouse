package zisis.aristofanis.animehouse.anime_list.presentation.state_contracts

import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.SideEffect
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.State

class AnimeContract {

    sealed class Event : IntentAction {
        object BackButtonPressed : Event()
        data class AnimeDetails(val anime: Anime) : Event()
    }

    sealed class ViewEffects : SideEffect {
        data class NavigateToAnimeDetails(val anime: Anime) : ViewEffects()
        object NavigateToAnimeList : ViewEffects()
    }

    data class ViewState(
        val previousScreen: AnimeScreen = AnimeScreen.EmptyScreen,
        val currentScreen: AnimeScreen = AnimeScreen.AnimeList
    ) : State

}

sealed class AnimeScreen {
    object AnimeList : AnimeScreen()
    object AnimeDetails : AnimeScreen()
    object EmptyScreen : AnimeScreen()
}
