package zisis.aristofanis.animehouse.presentation.presenters

import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.screen.HomeContract
import zisis.aristofanis.animehouse.type.MediaSort

class HomePresenter(
    private val view: HomeContract.View,
    private val getAnimeListUseCase: AnimeListUseCase
) :
    HomeContract.Presenter
{

    init {
        view.setPresenter(this)
        getAnimeSon()
    }

    override fun getAnimeSon() {
       val query: AnimeListQuery=  AnimeListQuery.builder().sort(listOf(MediaSort.TRENDING_DESC)).build()
        view.showLoading()
        getAnimeListUseCase(query) { handleResults(it) }
    }

    private fun handleResults(response: QueryData<AnimeListWithInfo>) {
        view.hideLoading()
        when (response) {
            is QueryData.Success<AnimeListWithInfo> -> view.showSuccess(response.data)
            is QueryData.Error<AnimeListWithInfo> -> view.showError(response.errorMessage)
        }
    }

    override fun terminate() {
       //do nothing
    }
}
