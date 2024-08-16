package location.domain

import location.domain.model.AddressRequestModel
import location.domain.model.AutocompleteResponseModel
import support.ResultDomain

interface LocationRepository {
    suspend fun getAddressSuggestion(addressRequest: AddressRequestModel): ResultDomain<AutocompleteResponseModel>
}
