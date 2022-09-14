package zisis.aristofanis.animehouse.anime_list.presentation.state_contracts

import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.anime_list.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.State

class AnimeListContract {

    sealed class AnimesEvent : IntentAction {
        data class AnimePressed(val anime: Anime) : AnimesEvent()
        data class GetAnimeListWithFilter(val filter: AnimeListUseCase.AnimeFilter) : AnimesEvent()
        object ErrorShown : AnimesEvent()
        object BackPressed : AnimesEvent()
        object AcknowledgeNavigation : AnimesEvent()
    }

    data class AnimesState(
        val loading: Boolean = false,
        val animesStatus: AnimesStatus = AnimesStatus.EmptyList,
        val animesNavigation: AnimesNavigation? = null,
        val genericError: String? = null
    ) : State


    sealed class AnimesNavigation {
        object NavigateBack : AnimesNavigation()
        data class NavigateToAnimeDetails(val anime: Anime) : AnimesNavigation()
    }

    sealed class AnimesStatus {
        object EmptyList : AnimesStatus()
        data class DisplayAnimeList(val animeList: AnimeListWithInfo) : AnimesStatus()
        data class ShowError(val errorText: String) : AnimesStatus()
    }
}
