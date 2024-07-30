package add_listing.data.data_source

import add_listing.data.api.CategoryApi
import add_listing.data.entity.CategoryEntity
import add_listing.data.entity.CategoryQueryRequest
import support.BaseDataSource
import support.Result

class LandingDataSourceImp(private val api: CategoryApi) : LandingDataSource, BaseDataSource() {
    override suspend fun getSuggestionCategories(categoryQueryRequest: CategoryQueryRequest): Result<List<CategoryEntity>> =
        getResult {
            executeNetworkAction {
                api.getCategories(categoryQueryRequest)
            }
        }
}