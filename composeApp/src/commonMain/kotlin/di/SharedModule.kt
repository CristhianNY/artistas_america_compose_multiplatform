package di

import auth.data.api.LoginApi
import auth.data.api.LoginApiImpl
import auth.data.data_source.AuthDataSource
import auth.data.data_source.AuthDataSourceImpl
import auth.data.repository.AuthRepositoryImpl
import auth.domain.repository.AuthRepository
import auth.presentation.AuthViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val sharedModule = module {
    // Define HttpClient singleton
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.i { "KtorLogger $message" }
                    }
                }
                level = LogLevel.ALL
            }
        }
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
