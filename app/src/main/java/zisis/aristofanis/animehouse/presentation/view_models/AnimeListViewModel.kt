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
import zisis.aristofanis.animehouse.presentation.activities.view_state.ViewState
import zisis.aristofanis.animehouse.type.MediaSort

class AnimeListViewModel(private val animeListUseCase: AnimeListUseCase) : ViewModel() {

    val uiModel: MutableLiveData<ViewState> = MutableLiveData(LoadingState(true))

    init {
        getAnimeList()
    }

    private fun getAnimeList() {
        val query: AnimeListQuery = AnimeListQuery.builder().sort(listOf(MediaSort.TRENDING_DESC)).build()
        return animeListUseCase(viewModelScope, query) {
            uiModel.value = LoadingState(false)
            uiModel.value = returnState(it)
        }
    }

    private fun returnState(queryData: QueryData<AnimeListWithInfo>): ViewState =
        when (queryData) {
            is QueryData.Success -> ResultState(queryData.data)
            is QueryData.Error -> ErrorState(queryData.errorMessage.message)
        }

}
