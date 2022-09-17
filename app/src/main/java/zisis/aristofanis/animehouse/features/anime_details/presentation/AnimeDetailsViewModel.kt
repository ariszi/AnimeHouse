package zisis.aristofanis.animehouse.features.anime_details.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import zisis.aristofanis.animehouse.features.anime_list.domain.models.Anime
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val savedState: SavedStateHandle
) : ViewModel() {

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val navigation: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val animeDetails: Flow<Anime> = savedState.getStateFlow<Anime?>("animeArg", Anime())
        .filterNotNull()
        .onStart { loading.update { true } }
        .onEach { loading.update { false } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = Anime()
        )

    val state: StateFlow<AnimeDetailsContract.AnimeDetailsState> =
        combine(loading, animeDetails, navigation) { loading, animeDetails, navigation ->
            animeDetails.toAnimeDetailsState(animeDetails, loading, navigation)
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = AnimeDetailsContract.AnimeDetailsState()
            )


    private fun Anime.toAnimeDetailsState(
        anime: Anime,
        loading: Boolean,
        navigateBack: Boolean
    ): AnimeDetailsContract.AnimeDetailsState {
        return AnimeDetailsContract.AnimeDetailsState(
            animeInfoSubState = AnimeDetailsContract.AnimeInfoSubState(
                title = anime.title.english,
                genre = anime.genres?.joinToString() ?: "No genres were found",
                description = anime.description,
                image = anime.image
            ),
            loading = loading,
            navigateBack = navigateBack
        )
    }

    fun consumeEvents(event: AnimeDetailsContract.Event) {
        when (event) {
            is AnimeDetailsContract.Event.BackPressed -> navigation.update { true }
            is AnimeDetailsContract.Event.AcknowledgeNavigation -> navigation.update { false }
        }
    }


}

inline fun <reified T : ViewModel> Fragment.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ) =
            viewModelProducer(handle) as T
    }
}
