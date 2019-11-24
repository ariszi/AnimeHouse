package zisis.aristofanis.animehouse.presentation.screen

import com.apollographql.apollo.api.Error
import zisis.aristofanis.animehouse.presentation.presenters.BasePresenter

interface BaseView<T:BasePresenter> {

    fun setPresenter(presenter: T)

    fun showLoading()

    fun hideLoading()

    fun showError(errorMessage: Error)
}
