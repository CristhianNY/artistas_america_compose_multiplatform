package org.artistasamerica.artistas.data.cities

import org.artistasamerica.artistas.data.category.CategoryEntity.primaryKey
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object CitiesEntity: Table<Nothing>("cities") {
    val id = int("idcities").primaryKey()
    val name  = varchar("name")
}