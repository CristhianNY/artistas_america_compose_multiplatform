package add_listing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import add_listing.domain.LandingRepository
import add_listing.domain.model.CategoryRequestModel
import add_listing.domain.model.CityRequestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import support.ResultDomain

class LandingViewModel(private val repository: LandingRepository) : ViewModel(), KoinComponent {

    private val _uiState = MutableStateFlow<LandingState>(LandingState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(ListingFormState())
    val formState = _formState.asStateFlow()


    private val _isCategorySelected = MutableStateFlow(false)
    val isCategorySelected = _isCategorySelected.asStateFlow()

    private val _isCitySelected = MutableStateFlow(false)
    val isCitySelected = _isCitySelected.asStateFlow()

    fun updateCategory(category: String, isSelected: Boolean = false) {
        _formState.value = _formState.value.copy(category = category)
        _isCategorySelected.value = isSelected
    }

    fun updateCity(city: String, isSelected: Boolean = false) {
        _formState.value = _formState.value.copy(city = city)
        _isCitySelected.value = isSelected
    }

    fun updateServiceName(serviceName: String) {
        _formState.value = _formState.value.copy(serviceName = serviceName)
    }

    fun updateAddress(address: String) {
        _formState.value = _formState.value.copy(address = address)
    }

    fun updateDescription(description: String) {
        _formState.value = _formState.value.copy(description = description)
    }

    fun updateImageUri(imageUri: String) {
        _formState.value = _formState.value.copy(imageUri = imageUri)
    }

    // Métodos para autocompletado y otros servicios
    fun getCategoryRecommendationsAutoCompleted(query: String) {
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

    fun getCitiesAutoCompleted(query: String) {
        _uiState.value = LandingState.Loading
        viewModelScope.launch {
            when (val result = repository.getSuggestionCities(CityRequestModel(query))) {
                is ResultDomain.Success -> {
                    _uiState.value = LandingState.SuggestionCity(result.data?.map { it.name })
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
