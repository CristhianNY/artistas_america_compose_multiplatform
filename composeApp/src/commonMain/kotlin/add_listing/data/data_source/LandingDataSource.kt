package add_listing.data.data_source

import add_listing.data.entity.CategoryEntity
import add_listing.data.entity.CategoryQueryRequest
import support.Result

interface LandingDataSource {

    suspend fun getSuggestionCategories(categoryQueryRequest: CategoryQueryRequest): Result<List<CategoryEntity>>
}