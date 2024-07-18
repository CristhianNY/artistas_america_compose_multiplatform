package org.artistasamerica.artistas.domain.error

object ErrorMapper {
    const val GENERIC_ERROR: String = "01"
    const val NEW_PHONE_NUMBER_ALREADY_REGISTERED_WITH_OTHER_USER = "02"
    const val PHONE_NUMBER_REQUIRED= "03"
    const val USER_NOT_FOUND = "04"
    const val PHONE_ALREADY_ASSOCIATED = "05"
    const val PHONE_ALREADY_EXISTS = "06"
    const val PHONE_ALREADY_REGISTERED = "07"
    const val ERROR_INSERTING = "08"
    const val INVALID_EMAIL_OR_PASSWORD = "09"
    const val INVALID_ID_TOKEN = "10"
}