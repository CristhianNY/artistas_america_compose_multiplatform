package add_listing.presentation

import support.ErrorDomain

sealed class LandingState {
    data object Idle : LandingState()
    data object Loading : LandingState()
    data class Success(val token: String) : LandingState()
    data class Error(val error: ErrorDomain) : LandingState()
    data class SuggestionCategory(val suggestions: List<String>?) : LandingState()
    data class SuggestionCity(val citySuggestions: List<String>?) : LandingState()
}