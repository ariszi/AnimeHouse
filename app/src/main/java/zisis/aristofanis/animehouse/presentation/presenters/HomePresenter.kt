package zisis.aristofanis.animehouse.presentation.presenters

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.presentation.screen.HomeContract
import kotlin.coroutines.CoroutineContext

class HomePresenter(
    private val view: HomeContract.View,
    private val getAnimeList: AnimeListUseCase,
    private val job: Job = Job(),
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main
) :
    HomeContract.Presenter,
    CoroutineScope {

    init {
        view.setPresenter(this)
    }

    override fun getAnimeSon() {
        view.showLoading()
        launch(job) {
            handleResults(getAnimeList.getAnimeListWithInfo())
        }
    }

    private fun handleResults(response: QueryData) {
        view.hideLoading()
        when (response) {
            is QueryData.Success<*> -> view.showSuccess(response.data as AnimeListWithInfo)
            is QueryData.Error -> view.showError(response.errorMessage)
        }
    }

    override fun terminate() {
        job.cancel()
    }
}
