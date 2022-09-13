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
    private val navigation: MutableStateFlow<AnimeListContractV2.AnimesNavigation?> =
        MutableStateFlow(null)
    private val showAnimes: MutableStateFlow<AnimeListContractV2.AnimesStatusV2> =
        MutableStateFlow(AnimeListContractV2.AnimesStatusV2.EmptyList)

    private val animes =
        flow {
            emit(
                when (val animes = animeListUseCase.invoke()) {
                    is Success -> {
                        AnimeListContractV2.AnimesStatusV2.DisplayAnimeList(animes.data)
                    }
                    is Error -> {
                        AnimeListContractV2.AnimesStatusV2.ShowError("Something is up")
                    }
                }
            )
        }
            .onStart { loading.update { true } }
            .onEach { loading.update { false } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = AnimeListContractV2.AnimesStatusV2.EmptyList
            )


    private fun retrieveAnimes() {
        showAnimes.update { animes.value }
    }

    val state: StateFlow<AnimeListContractV2.AnimesState> =
        combine(loading, animes, navigation) { loading, animes, navigation ->
            AnimeListContractV2.AnimesState(
                loading,
                animes,
                navigation
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
                retrieveAnimes()
            }
            is AnimeListContractV2.AnimesEvent.AnimePressed -> {
                navigateToAnimeDetails(event.anime)
            }
            is AnimeListContractV2.AnimesEvent.BackPressed -> {
                navigateUserBack()
            }
            is AnimeListContractV2.AnimesEvent.AcknowledgeNavigation -> {
                acknowledgeNavigation()
            }
        }
    }

    private fun acknowledgeNavigation() {
        navigation.update { null }
    }

    private fun navigateUserBack() {
        navigation.update { AnimeListContractV2.AnimesNavigation.NavigateBack }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        navigation.update { AnimeListContractV2.AnimesNavigation.NavigateToAnimeDetails(anime) }
    }


}



