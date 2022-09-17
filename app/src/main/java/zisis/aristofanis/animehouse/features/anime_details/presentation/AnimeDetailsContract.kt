package zisis.aristofanis.animehouse.features.anime_details.presentation

import zisis.aristofanis.animehouse.core.presentation.base_contracts.IntentAction
import zisis.aristofanis.animehouse.core.presentation.base_contracts.State
import zisis.aristofanis.animehouse.core.utils.EMPTY
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Anime

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
