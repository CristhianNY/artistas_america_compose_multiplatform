package location.data.repository

import location.data.data_source.LocationDataSource
import location.data.entity.toEntity
import location.data.entity.toModel
import location.domain.LocationRepository
import location.domain.model.AddressRequestModel
import location.domain.model.AutocompleteResponseModel
import support.GenericErrorMapper
import support.ResultDomain
import support.baseResponseErrorHandler

class LocationRepositoryImpl(private val dataSource: LocationDataSource) : LocationRepository {
    override suspend fun getAddressSuggestion(addressRequest: AddressRequestModel): ResultDomain<AutocompleteResponseModel> =
        baseResponseErrorHandler(
            GenericErrorMapper, dataSource.getAddressSuggestion(addressRequest.toEntity())
        ) { ResultDomain.Success(it.toModel()) }
}
