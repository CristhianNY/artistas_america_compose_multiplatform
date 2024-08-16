package location.data.data_source

import location.data.entity.AddressRequestEntity
import location.data.entity.AutocompleteResponseEntity
import support.Result

interface LocationDataSource {
    suspend fun getAddressSuggestion(addressRequest: AddressRequestEntity): Result<AutocompleteResponseEntity>
}