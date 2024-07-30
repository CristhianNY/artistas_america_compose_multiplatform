package add_listing.data.repository

import add_listing.data.data_source.LandingDataSource
import add_listing.data.entity.toModel
import add_listing.domain.LandingRepository
import add_listing.domain.model.CategoryModel
import add_listing.domain.model.CategoryRequestModel
import add_listing.domain.model.toEntity
import support.GenericErrorMapper
import support.ResultDomain
import support.baseResponseErrorHandler

class LandingRepositoryImpl(private val dataSource: LandingDataSource) : LandingRepository {
    override suspend fun getSuggestionCategories(categoryRequestModel: CategoryRequestModel): ResultDomain<List<CategoryModel>> =
        baseResponseErrorHandler(
            GenericErrorMapper, dataSource.getSuggestionCategories(categoryRequestModel.toEntity())
        ) { ResultDomain.Success(it.map { item -> item.toModel() }) }
}