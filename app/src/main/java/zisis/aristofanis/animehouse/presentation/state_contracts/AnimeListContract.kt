package zisis.aristofanis.animehouse.presentation.state_contracts

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.SideEffect
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.State

class AnimeListContract() {

    sealed class Event : IntentAction {
        data class ListItemClickIntentAction(val anime: Anime) : Event()
        data class LaunchAnimeListIntentAction(val filter: AnimeListUseCase.AnimeFilter) : Event() {

            @InternalCoroutinesApi
            suspend fun getAnimeList(animeListUseCase: AnimeListUseCase): Flow<AnimeList> {
                return animeListUseCase(filter)
            }
        }
    }

    sealed class ViewEffects : SideEffect {
        data class NavigateToAnimeDetails(val anime: Anime) : ViewEffects()
    }

    data class ViewState(
        val animeList: AnimeList = AnimeList.Loading(true),
        val filter: AnimeListUseCase.AnimeFilter = AnimeListUseCase.AnimeFilter()
    ) : State
}

sealed class AnimeList {
    object NoChangesOnList : AnimeList()
    data class Loading(val loading: Boolean) : AnimeList()
    data class AnimeListItems(val animeList: AnimeListWithInfo) : AnimeList()
    data class ListError(val errorText: String) : AnimeList()
}
