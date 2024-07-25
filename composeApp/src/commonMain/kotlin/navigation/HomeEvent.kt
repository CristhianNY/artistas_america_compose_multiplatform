package navigation

sealed interface HomeEvent{
    data object GoToDashboard : HomeEvent
}