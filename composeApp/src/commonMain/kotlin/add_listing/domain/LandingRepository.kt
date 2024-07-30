package add_listing.domain

import add_listing.domain.model.CategoryModel
import add_listing.domain.model.CategoryRequestModel
import add_listing.domain.model.CityModel
import add_listing.domain.model.CityRequestModel
import support.ResultDomain

interface LandingRepository {

    suspend fun getSuggestionCategories(categoryRequestModel: CategoryRequestModel): ResultDomain<List<CategoryModel>>

    suspend fun getSuggestionCities(cityRequestModel: CityRequestModel): ResultDomain<List<CityModel>>

}