package navigation.home

sealed interface HomeEvent{
    data object GoToDashboard : HomeEvent
    data object GoToJoin: HomeEvent
}