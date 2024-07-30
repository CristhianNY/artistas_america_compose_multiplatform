package add_listing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import add_listing.domain.LandingRepository
import add_listing.domain.model.CategoryRequestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import support.ResultDomain

class LandingViewModel(private val repository: LandingRepository) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow<LandingState>(LandingState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getCategoryRecommendationsAutoCompleted(query: String) {
        _uiState.value = LandingState.Loading
        viewModelScope.launch {
            when (val result = repository.getSuggestionCategories(CategoryRequestModel(query))) {
                is ResultDomain.Success -> {
                    val suggestions = result.data?.map { it.name }
                    _uiState.value = LandingState.SuggestionCategory(suggestions)
                }

                is ResultDomain.Error -> {
                    _uiState.value = LandingState.Error(result.error)
                }
            }
        }
    }

    fun clearState() {
        _uiState.value = LandingState.Idle
    }
}
