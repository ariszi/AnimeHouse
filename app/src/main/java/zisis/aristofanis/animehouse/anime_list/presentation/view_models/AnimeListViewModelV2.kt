package zisis.aristofanis.animehouse.anime_list.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import zisis.aristofanis.animehouse.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.anime_list.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.anime_list.presentation.state_contracts.AnimeListContractV2
import zisis.aristofanis.animehouse.core.domain.Result.Error
import zisis.aristofanis.animehouse.core.domain.Result.Success
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class AnimeListViewModelV2 @Inject constructor(private val animeListUseCase: AnimeListUseCase) :
    ViewModel() {


    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val effects: MutableStateFlow<AnimeListContractV2.AnimesEffects> =
        MutableStateFlow(AnimeListContractV2.AnimesEffects())
    private val animes =
        flow {
            emit(
                animeListUseCase.invoke()
//                when ( result ) {
//                    is Success -> AnimeListContractV2.AnimeListStatus.DisplayAnimeList(result.data)
//                    is Error -> effects.update { AnimeListContractV2.AnimeListEffects(errorToast = "Something went wrong") }
//                }
            )
        }
            .onStart { loading.update { true } }
            .onEach { loading.update { false } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = null
            )

    val state: StateFlow<AnimeListContractV2.AnimesState> =
        combine(loading, animes, effects) { loading, animes, effects ->
            var animeList: AnimeListContractV2.AnimesStatusV2 =
                AnimeListContractV2.AnimesStatusV2.IdleStatus
            when (animes) {
                is Success -> {
                    animeList = AnimeListContractV2.AnimesStatusV2.DisplayAnimeList(animes.data)
                }
                is Error -> {
                    effects.copy(errorText = "Something went wrong")
                    animeList = AnimeListContractV2.AnimesStatusV2.EmptyList
                }
            }
            AnimeListContractV2.AnimesState(
                loading,
                animeList,
                effects
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AnimeListContractV2.AnimesState()
            )


    fun consumeEvent(event: AnimeListContractV2.AnimesEvent) {
        when (event) {
            is AnimeListContractV2.AnimesEvent.GetAnimeListWithFilter -> {
                // getAnimeList()
            }
            is AnimeListContractV2.AnimesEvent.ListItemClickIntentAction -> {
                navigateToAnimeDetails(event.anime)
            }
            is AnimeListContractV2.AnimesEvent.ErrorShown -> {
                effects.update { AnimeListContractV2.AnimesEffects() }
            }
        }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        effects.update { AnimeListContractV2.AnimesEffects(navigateToAnimeDetails = anime) }
    }


}



