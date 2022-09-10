package zisis.aristofanis.animehouse.anime_list.presentation.state_contracts

import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.anime_list.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.State

class AnimeListContractV2() {

    sealed class AnimesEvent : IntentAction {
        data class ListItemClickIntentAction(val anime: Anime) : AnimesEvent()
        data class GetAnimeListWithFilter(val filter: AnimeListUseCase.AnimeFilter) : AnimesEvent()
        object ErrorShown : AnimesEvent()
    }

    data class AnimesState(
        val loading: Boolean = false,
        val animesStatus: AnimesStatusV2 = AnimesStatusV2.IdleStatus,
        val effects: AnimesEffects = AnimesEffects()
    ) : State

    data class AnimesEffects(
        val backPressed: Boolean = false,
        val navigateToAnimeDetails: Anime? = null,
        val errorText: String? = null
    )

    sealed class AnimesStatusV2 {
        object IdleStatus : AnimesStatusV2()
        object EmptyList : AnimesStatusV2()
        data class DisplayAnimeList(val animeList: AnimeListWithInfo) : AnimesStatusV2()
        data class ShowError(val errorText: String) : AnimesStatusV2()
    }
}
