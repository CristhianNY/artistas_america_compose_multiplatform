package add_listing.data.data_source

import add_listing.data.api.LandingApi
import add_listing.data.entity.CategoryEntity
import add_listing.data.entity.CategoryQueryRequest
import add_listing.data.entity.CityEntity
import add_listing.data.entity.CityQueryRequest
import support.BaseDataSource
import support.Result

class LandingDataSourceImp(private val api: LandingApi) : LandingDataSource, BaseDataSource() {
    override suspend fun getSuggestionCategories(categoryQueryRequest: CategoryQueryRequest): Result<List<CategoryEntity>> =
        getResult {
            executeNetworkAction {
                api.getCategories(categoryQueryRequest)
            }
        }

    override suspend fun getSuggestionCities(categoryQueryRequest: CityQueryRequest): Result<List<CityEntity>> =
        getResult {
            executeNetworkAction {
                api.getCities(categoryQueryRequest)
            }
        }
}