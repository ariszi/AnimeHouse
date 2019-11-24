package zisis.aristofanis.animehouse.presentation.screen

import zisis.aristofanis.animehouse.domain.AnimeListWithInfo
import zisis.aristofanis.animehouse.presentation.presenters.BasePresenter

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun showSuccess(animeListWithInfo: AnimeListWithInfo)
    }

    interface Presenter : BasePresenter {
        fun getAnimeSon()
    }


}
