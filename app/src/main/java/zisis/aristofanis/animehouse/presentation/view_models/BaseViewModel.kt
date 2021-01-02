package zisis.aristofanis.animehouse.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import zisis.aristofanis.animehouse.presentation.state_management.Action
import zisis.aristofanis.animehouse.presentation.state_management.SideEffects
import zisis.aristofanis.animehouse.presentation.state_management.State

@ExperimentalCoroutinesApi
abstract class BaseViewModel<S : State, I : Action, SE : SideEffects> constructor(initialState: S, initialIntentAction: I) :
    ViewModel() {

    private val mutableStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val sideEffects = Channel<SE>()

    init {
        onIntentAction(initialIntentAction)
    }

    fun reduceToState(reduceActionEvent: suspend () -> Flow<S>) {
        viewModelScope.launch {
            val newStateFlow = reduceActionEvent().stateIn(this)
            newStateFlow.collect {
                mutableStateFlow.value = it
                log(it)
            }
        }
    }

    fun consumeSideEffect(handleSideEffect: () -> SE) {
        val sideEffect = handleSideEffect()
        log(sideEffect)
        viewModelScope.launch { sideEffects.send(sideEffect) }
    }

    fun onIntentAction(action: I) = viewModelScope.launch {
        log(action)
        reduceIntentAction(action)
    }

    abstract suspend fun reduceIntentAction(intentAction: I)

    //exposed to View
    val currentStateFlow: StateFlow<S> = mutableStateFlow.asStateFlow()

    val eventSideEffects: Flow<SE> = sideEffects.receiveAsFlow()

    private fun log(action: I) = Timber
        .tag("${this.javaClass.simpleName} - Intent Action")
        .i(action.toString())

    private fun log(state: S) = Timber
        .tag("${this.javaClass.simpleName} - State")
        .i(state.toString())

    private fun log(sideEffect: SE) = Timber
        .tag("${this.javaClass.simpleName} - Side effect")
        .i(sideEffect.toString())

}
