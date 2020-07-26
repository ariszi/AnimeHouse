package zisis.aristofanis.animehouse.presentation.activities.view_state

import android.view.View

sealed class ViewState

data class ResultState<R>(val result: R) : ViewState()
data class ErrorState(val errorText: String) : ViewState()
data class LoadingState(val loading: Boolean) : ViewState() {
    fun loadingVisibility(): Int = if (loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

object ExitState : ViewState()


