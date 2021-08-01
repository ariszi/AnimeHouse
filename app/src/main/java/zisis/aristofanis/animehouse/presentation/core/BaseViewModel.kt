package zisis.aristofanis.animehouse.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.IntentAction
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.SideEffect
import zisis.aristofanis.animehouse.presentation.state_contracts.base_contracts.State

@ExperimentalCoroutinesApi
abstract class BaseViewModel<S : State, I : IntentAction, SE : SideEffect> constructor(initialState: S) :
    ViewModel() {

    private val mutableStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val sideEffects = Channel<SE>()

    fun emitState(reduceState: S.() -> S) {
        val newState = state.value.reduceState()
        mutableStateFlow.value = newState
        log(newState)
    }

    fun consumeSideEffect(handleSideEffect: () -> SE) {
        val sideEffect = handleSideEffect()
        log(sideEffect)
        viewModelScope.launch { sideEffects.send(sideEffect) }
    }

    fun setIntentAction(action: I) = viewModelScope.launch {
        log(action)
        consumeIntentAction(action)
    }

    abstract suspend fun consumeIntentAction(intentAction: I)

    //exposed to View
    val state: StateFlow<S> = mutableStateFlow.asStateFlow()

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
