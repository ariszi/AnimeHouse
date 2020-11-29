package zisis.aristofanis.animehouse.presentation.state_management

import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo

sealed class State()

data class ShowAnimeListState(val animeList: AnimeListWithInfo) : State()
object IdleState : State()
object ExitState : State()
data class ShowAnimeState(val result: Anime) : State()
data class ErrorState(val errorText: String) : State()
data class LoadingState(val loading: Boolean) : State()

val genericErrorState = ErrorState("Som Ting Wong")
