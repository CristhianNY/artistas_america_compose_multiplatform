package navigation.lading

sealed interface LandingEvent {
    data object GoToDashboard : LandingEvent
    data object GoToServiceActorNameScreen : LandingEvent
    data object GoBack : LandingEvent
}