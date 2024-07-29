package navigation.lading

sealed interface LandingEvent {
    data object GoToDashboard : LandingEvent
}