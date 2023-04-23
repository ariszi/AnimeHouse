package zisis.aristofanis.animehouse.features.anime_list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import zisis.aristofanis.animehouse.core.domain.Result.Error
import zisis.aristofanis.animehouse.core.domain.Result.Success
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Anime
import zisis.aristofanis.animehouse.features.anime_list.domain.usecases.AnimeListUseCase
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val animeListUseCase: AnimeListUseCase,
    private val savedState: SavedStateHandle
) : ViewModel() {

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val navigation: MutableStateFlow<AnimeListContract.AnimesNavigation?> =
        MutableStateFlow(null)
    private val showAnimes: MutableStateFlow<AnimeListContract.AnimesStatus> =
        MutableStateFlow(AnimeListContract.AnimesStatus.EmptyList)

    private val animes =
        flow {
            emit(
                when (val animes = animeListUseCase.invoke()) {
                    is Success -> {
                        AnimeListContract.AnimesStatus.DisplayAnimeList(animes.data)
                    }
                    is Error -> {
                        AnimeListContract.AnimesStatus.ShowError("Something is up")
                    }
                }
            )
        }
            .onStart { loading.update { true } }
            .onEach { loading.update { false } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = AnimeListContract.AnimesStatus.EmptyList
            )


    private fun retrieveAnimes() {
        showAnimes.update { animes.value }
    }

    val state: StateFlow<AnimeListContract.AnimesState> =
        combine(loading, animes, navigation) { loading, animes, navigation ->
            AnimeListContract.AnimesState(
                loading,
                animes,
                navigation
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AnimeListContract.AnimesState()
            )


    fun consumeEvent(event: AnimeListContract.AnimesEvent) {
        when (event) {
            is AnimeListContract.AnimesEvent.GetAnimeListWithFilter -> {
                retrieveAnimes()
            }
            is AnimeListContract.AnimesEvent.AnimePressed -> {
                navigateToAnimeDetails(event.anime)
            }
            is AnimeListContract.AnimesEvent.BackPressed -> {
                navigateUserBack()
            }
            is AnimeListContract.AnimesEvent.AcknowledgeNavigation -> {
                acknowledgeNavigation()
            }

            AnimeListContract.AnimesEvent.ErrorShown -> TODO()
        }
    }

    private fun acknowledgeNavigation() {
        navigation.update { null }
    }

    private fun navigateUserBack() {
        navigation.update { AnimeListContract.AnimesNavigation.NavigateBack }
    }

    private fun navigateToAnimeDetails(anime: Anime) {
        navigation.update { AnimeListContract.AnimesNavigation.NavigateToAnimeDetails(anime) }
    }


}



