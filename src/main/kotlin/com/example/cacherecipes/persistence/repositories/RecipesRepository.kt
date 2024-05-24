package com.example.cacherecipes.persistence.repositories

import com.example.cacherecipes.persistence.entities.Recipes
import org.springframework.data.jpa.repository.JpaRepository

interface RecipesRepository: JpaRepository<Recipes,Long> {
}