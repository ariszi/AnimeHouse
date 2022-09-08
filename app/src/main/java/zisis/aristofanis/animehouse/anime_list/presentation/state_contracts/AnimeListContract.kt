package zisis.aristofanis.animehouse.anime_list.presentation.state_contracts

import kotlinx.coroutines.InternalCoroutinesApi
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.anime_list.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.SideEffect
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.base_contracts.State
import zisis.aristofanis.animehouse.core.domain.Result

class AnimeListContract() {

    sealed class Event : IntentAction {
        data class ListItemClickIntentAction(val anime: Anime) : Event()
        data class GetAnimeListWithFilter(val filter: AnimeListUseCase.AnimeFilter) : Event() {

            @InternalCoroutinesApi
            suspend fun getAnimeList(animeListUseCase: AnimeListUseCase): AnimeListStatus {
                return when(val result = animeListUseCase.invoke(filter)){
                    is Result.Success -> AnimeListStatus.DisplayAnimeList(result.data)
                    is Result.Error -> AnimeListStatus.ShowError("Something went wrong")
                }
            }
        }
    }

    sealed class ViewEffects : SideEffect {
        data class NavigateToAnimeDetails(val anime: Anime) : ViewEffects()
    }

    data class ViewState(
        val animeListStatus: AnimeListStatus = AnimeListStatus.Loading(true),
        val filter: AnimeListUseCase.AnimeFilter = AnimeListUseCase.AnimeFilter()
    ) : State
}

sealed class AnimeListStatus {
    object IdleStatus : AnimeListStatus()
    data class Loading(val loading: Boolean) : AnimeListStatus()
    data class DisplayAnimeList(val animeList: AnimeListWithInfo) : AnimeListStatus()
    data class ShowError(val errorText: String) : AnimeListStatus()
}
