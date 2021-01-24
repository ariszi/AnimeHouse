package zisis.aristofanis.animehouse.presentation.state_management

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.type.MediaSort
import zisis.aristofanis.animehouse.type.MediaStatus

sealed class AnimeListContract {

    sealed class Event : IntentAction {
        data class ListItemClickIntentAction(val anime: Anime) : Event()
        data class LaunchAnimeListIntentAction(val filter: AnimeFilter, val animeListUseCase: AnimeListUseCase) : Event() {
            fun filterToQuery(): AnimeListQuery {
                val query = AnimeListQuery.builder()
                filter.id?.let { query.id(it) }
                filter.page?.let { query.page(it) }
                filter.perPage?.let { query.perPage(it) }
                filter.search?.let { query.search(it) }
                filter.genre?.let { query.genre(it) }
                filter.status?.let { query.status(it) }
                filter.sort?.let { query.sort(it) }
                return query.build()
            }
        }
    }

    sealed class ViewEffects : SideEffects {

        data class ShowAnimeState(val result: Anime) : ViewEffects()
    }

    data class ViewState(
        val animeList: AnimeList = AnimeList.Loading(true),
        val filter: AnimeFilter = AnimeFilter()
    ) : State
}

data class AnimeFilter(
    val id: Int? = null,
    val page: Int? = null,
    val perPage: Int? = null,
    val search: String? = null,
    val genre: String? = null,
    val status: MediaStatus? = null,
    val sort: List<MediaSort>? = listOf(MediaSort.POPULARITY_DESC)
)

sealed class AnimeList {
    object NoChangesOnList : AnimeList()
    data class Loading(val loading: Boolean) : AnimeList()
    data class AnimeListItems(val animeList: AnimeListWithInfo) : AnimeList()
    data class ListError(val errorText: String) : AnimeList()
}
