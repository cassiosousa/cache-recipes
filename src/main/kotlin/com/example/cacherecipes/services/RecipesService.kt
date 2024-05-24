package com.example.cacherecipes.services

import com.example.cacherecipes.Either
import com.example.cacherecipes.persistence.entities.Recipes
import com.example.cacherecipes.usecases.CacheRecipesUseCase
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipesService(
    val cacheRecipesUseCase: CacheRecipesUseCase
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun getRecipeById(id: Long): Either<String, Recipes> {
        logger.info { "[RecipeService] searching recipe with id:$id" }
        return cacheRecipesUseCase.invoke(id)
    }
}