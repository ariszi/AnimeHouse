package zisis.aristofanis.animehouse.presentation.presenters

import AnimeListQuery
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.screen.HomeContract

class HomePresenter(
    private val view: HomeContract.View,
    private val getAnimeListUseCase: AnimeListUseCase
) :
    HomeContract.Presenter
{

    init {
        view.setPresenter(this)
    }

    override fun getAnimeSon() {
        view.showLoading()
        getAnimeListUseCase(AnimeListQuery.builder().build()) { handleResults(it) }
    }

    private fun handleResults(response: QueryData<AnimeListWithInfo>) {
        view.hideLoading()
        when (response) {
            is QueryData.Success<AnimeListWithInfo> -> view.showSuccess(response.data)
            is QueryData.Error<AnimeListWithInfo> -> view.showError(response.errorMessage)
        }
    }

    override fun terminate() {
        getAnimeListUseCase.unregister()
    }
}
