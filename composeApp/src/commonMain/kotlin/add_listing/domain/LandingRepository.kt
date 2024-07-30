package add_listing.domain

import add_listing.domain.model.CategoryModel
import add_listing.domain.model.CategoryRequestModel
import support.ResultDomain

interface LandingRepository {

    suspend fun getSuggestionCategories(categoryRequestModel: CategoryRequestModel): ResultDomain<List<CategoryModel>>
}