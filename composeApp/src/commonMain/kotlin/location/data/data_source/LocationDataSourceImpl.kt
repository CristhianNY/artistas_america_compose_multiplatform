package location.data.data_source

import location.data.api.LocationApi
import location.data.entity.AddressRequestEntity
import location.data.entity.AutocompleteResponseEntity
import support.BaseDataSource
import support.Result

class LocationDataSourceImpl(private val api: LocationApi) : LocationDataSource, BaseDataSource() {

    override suspend fun getAddressSuggestion(addressRequest: AddressRequestEntity): Result<AutocompleteResponseEntity> =
        getResult {
            executeNetworkAction {
                api.getAddressSuggestion(addressRequest)
            }
        }
}