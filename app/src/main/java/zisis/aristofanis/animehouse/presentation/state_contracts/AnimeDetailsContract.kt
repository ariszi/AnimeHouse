package zisis.aristofanis.animehouse.presentation.state_contracts

import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.SideEffect
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.State

class AnimeDetailsContract {

    sealed class Event : IntentAction {

    }

    sealed class ViewEffects : SideEffect {

    }

    data class ViewState(
        val data: AnimeData = AnimeData.Loading(true)
    ) : State

}

sealed class AnimeData {
    data class AnimeDetails(val anime: Anime) : AnimeData()
    data class Loading(val loading: Boolean) : AnimeData()
}

