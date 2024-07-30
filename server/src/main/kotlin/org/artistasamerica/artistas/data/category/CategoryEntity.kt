package org.artistasamerica.artistas.data.category

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object CategoryEntity: Table<Nothing>("category") {
    val id = int("idcategory").primaryKey()
    val name  = varchar("name")
}