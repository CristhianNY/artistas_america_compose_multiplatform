package add_listing.data.data_source

import add_listing.data.entity.CategoryEntity
import add_listing.data.entity.CategoryQueryRequest
import add_listing.data.entity.CityEntity
import add_listing.data.entity.CityQueryRequest
import support.Result

interface LandingDataSource {

    suspend fun getSuggestionCategories(categoryQueryRequest: CategoryQueryRequest): Result<List<CategoryEntity>>

    suspend fun getSuggestionCities(categoryQueryRequest: CityQueryRequest): Result<List<CityEntity>>
}