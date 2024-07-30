package di

import auth.data.api.LoginApi
import auth.data.api.LoginApiImpl
import auth.data.data_source.AuthDataSource
import auth.data.data_source.AuthDataSourceImpl
import auth.data.repository.AuthRepositoryImpl
import auth.domain.repository.AuthRepository
import auth.presentation.AuthViewModel
import add_listing.data.api.CategoryApi
import add_listing.data.api.CategoryApiImpl
import add_listing.data.data_source.LandingDataSource
import add_listing.data.data_source.LandingDataSourceImp
import add_listing.data.repository.LandingRepositoryImpl
import add_listing.domain.LandingRepository
import add_listing.presentation.LandingViewModel
import navigation.UrlHandler
import org.koin.dsl.module
import support.createHttpClient

val sharedModule = module {
    // Define HttpClient singleton
    single {
        createHttpClient()
    }

    // Define LoginApi singleton
    single<LoginApi> { LoginApiImpl(get()) }

    // Define AuthDataSource singleton
    single<AuthDataSource> { AuthDataSourceImpl(get()) }

    // Define AuthRepository singleton
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    // Define AuthViewModel singleton
    single {
        AuthViewModel(get())
    }

    single {
        LandingViewModel(get())
    }

    // Define UrlHandler singleton
    single { UrlHandler() }

    // Define CategoryApi singleton
    single<CategoryApi> { CategoryApiImpl(get()) }

    // Define LandingDataSource singleton
    single<LandingDataSource> { LandingDataSourceImp(get()) }

    // Define LandingRepository singleton
    single<LandingRepository> { LandingRepositoryImpl(get()) }
}
