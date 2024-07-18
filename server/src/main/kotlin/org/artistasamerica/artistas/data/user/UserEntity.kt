package org.artistasamerica.artistas.data.user

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserEntity: Table<Nothing>("users") {
    val id = int("id").primaryKey()
    val name  = varchar("name")
    val lastName = varchar("lastName")
    val email = varchar("email")
    val password = varchar("password")
    val subscription = int("subscription")
    val idToken = varchar("idToken")
}