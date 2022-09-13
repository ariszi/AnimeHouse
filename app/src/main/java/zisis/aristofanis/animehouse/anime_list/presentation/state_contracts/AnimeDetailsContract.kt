package zisis.aristofanis.animehouse.anime_list.presentation.state_contracts

import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.State
import zisis.aristofanis.animehouse.core.utils.EMPTY

class AnimeDetailsContract {

    sealed class Event : IntentAction {
        object BackPressed : Event()
        object AcknowledgeNavigation : Event()
        data class AnimeDetails(val anime: Anime) : Event()
    }

    data class AnimeDetailsState(
        val animeInfoSubState: AnimeInfoSubState = AnimeInfoSubState(),
        val loading: Boolean = false,
        val navigateBack: Boolean = false
    ) : State

    data class AnimeInfoSubState(
        val title: String = EMPTY,
        val genre: String = EMPTY,
        val description: String = EMPTY,
        val image: String = EMPTY
    )

}
