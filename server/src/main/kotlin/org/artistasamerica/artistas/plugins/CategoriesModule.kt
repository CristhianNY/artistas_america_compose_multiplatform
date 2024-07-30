package org.artistasamerica.artistas.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.artistasamerica.artistas.data.category.CategoryEntity
import org.artistasamerica.artistas.data.db.DatabaseConnection
import org.artistasamerica.artistas.data.category.CategoryQueryRequest
import org.artistasamerica.artistas.data.category.CategoryResponse
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where

fun Application.categoriesModule() {
    val db = DatabaseConnection.database
    routing {
        get("/categories") {
            val categories = db.from(CategoryEntity)
                .select()
                .mapNotNull { row -> row[CategoryEntity.name] }
                .toList()
            call.respond(categories)
        }

        post("/categories/search") {
            val categoryQueryRequest = call.receive<CategoryQueryRequest>()
            val searchQuery = "%${categoryQueryRequest.query}%"

            val matchingCategories = db.from(CategoryEntity)
                .select()
                .where { CategoryEntity.name like searchQuery }
                .mapNotNull { row ->
                    CategoryResponse(
                        id = row[CategoryEntity.id] ?: 0,
                        name = row[CategoryEntity.name] ?: ""
                    )
                }
                .toList()

            call.respond(matchingCategories)
        }
    }
}