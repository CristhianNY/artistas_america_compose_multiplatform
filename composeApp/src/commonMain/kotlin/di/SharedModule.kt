package di

import auth.data.api.AuthApi
import auth.data.api.AuthApiImpl
import auth.data.data_source.AuthDataSource
import auth.data.data_source.AuthDataSourceImpl
import auth.data.repository.AuthRepositoryImpl
import auth.domain.repository.AuthRepository
import auth.presentation.AuthViewModel
import add_listing.data.api.LandingApi
import add_listing.data.api.LandingApiImpl
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
    single<AuthApi> { AuthApiImpl(get()) }

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
    single<LandingApi> { LandingApiImpl(get()) }

    // Define LandingDataSource singleton
    single<LandingDataSource> { LandingDataSourceImp(get()) }

    // Define LandingRepository singleton
    single<LandingRepository> { LandingRepositoryImpl(get()) }
}
