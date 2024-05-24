package com.example.cacherecipes.persistence.repositories

import com.example.cacherecipes.persistence.entities.RecipesError
import org.springframework.data.jpa.repository.JpaRepository

interface RecipesErrorRepository : JpaRepository<RecipesError, Long> {
    fun findTop5ByStatus(status: String = "NEW"): List<RecipesError>
}