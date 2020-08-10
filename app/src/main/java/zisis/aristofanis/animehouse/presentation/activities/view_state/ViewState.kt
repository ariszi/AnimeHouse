package zisis.aristofanis.animehouse.presentation.activities.view_state

import android.view.View

sealed class State

data class ResultState<R>(val result: R) : State()
data class ErrorState(val errorText: String) : State()
data class LoadingState(val loading: Boolean) : State() {
    fun loadingVisibility(): Int = if (loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

object ExitState : State()


