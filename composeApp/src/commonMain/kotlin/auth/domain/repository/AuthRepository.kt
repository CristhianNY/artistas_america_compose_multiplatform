package auth.domain.repository

interface AuthRepository {
    suspend fun login()
}