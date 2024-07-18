package org.artistasamerica.artistas.data.db

import org.ktorm.database.Database

object DatabaseConnection {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/artistas_america",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = ""
    )
}