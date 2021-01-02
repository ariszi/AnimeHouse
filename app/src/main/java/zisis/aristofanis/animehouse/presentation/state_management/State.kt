package zisis.aristofanis.animehouse.presentation.state_management

interface State

sealed class InitialStates: State {

    object InitialState: InitialStates()

}
