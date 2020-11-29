package zisis.aristofanis.animehouse.presentation.state_management

import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase

sealed class UserIntents()


data class ListItemClickIntentAction(val anime: ShowAnimeState) : UserIntents()

data class LaunchAnimeListAction(
    val animeListUseCase: AnimeListUseCase
): UserIntents()


