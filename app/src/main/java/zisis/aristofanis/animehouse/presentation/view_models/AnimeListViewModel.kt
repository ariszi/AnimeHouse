package zisis.aristofanis.animehouse.presentation.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.activities.view_state.ErrorState
import zisis.aristofanis.animehouse.presentation.activities.view_state.LoadingState
import zisis.aristofanis.animehouse.presentation.activities.view_state.ResultState
import zisis.aristofanis.animehouse.presentation.activities.view_state.State
import zisis.aristofanis.animehouse.type.MediaSort

class AnimeListViewModel(private val animeListUseCase: AnimeListUseCase) : ViewModel() {

    val stateModel: MutableLiveData<State> = MutableLiveData(LoadingState(true))

    init {
        getAnimeList()
    }

    private fun getAnimeList() {
        val query: AnimeListQuery = AnimeListQuery.builder().sort(listOf(MediaSort.TRENDING_DESC)).build()
        return animeListUseCase(viewModelScope, query) {
            stateModel.value = LoadingState(false)
            stateModel.value = returnState(it)
        }
    }

    private fun returnState(queryData: QueryData<AnimeListWithInfo>): State =
        when (queryData) {
            is QueryData.Success -> ResultState(queryData.data)
            is QueryData.Error -> ErrorState(queryData.errorMessage.message)
        }

}
