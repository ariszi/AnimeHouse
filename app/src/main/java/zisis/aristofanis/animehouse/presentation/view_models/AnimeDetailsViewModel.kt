package zisis.aristofanis.animehouse.presentation.view_models

import kotlinx.coroutines.ExperimentalCoroutinesApi
import zisis.aristofanis.animehouse.domain.models.Anime
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeData.AnimeDetails
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeDetailsContract

@ExperimentalCoroutinesApi
class AnimeDetailsViewModel(val anime: Anime) :
    BaseViewModel<AnimeDetailsContract.ViewState, AnimeDetailsContract.Event, AnimeDetailsContract.ViewEffects>(
        AnimeDetailsContract.ViewState(AnimeDetails(anime))
    ) {
    
    override suspend fun consumeIntentAction(intentAction: AnimeDetailsContract.Event) {

    }
}
