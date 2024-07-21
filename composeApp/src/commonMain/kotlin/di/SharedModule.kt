package di

import auth.data.api.LoginApi
import auth.data.api.LoginApiImpl
import auth.data.data_source.AuthDataSource
import auth.data.data_source.AuthDataSourceImpl
import auth.data.repository.AuthRepositoryImpl
import auth.domain.repository.AuthRepository
import auth.presentation.AuthViewModel
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

    single {
        AuthViewModel(get())
    }
}
