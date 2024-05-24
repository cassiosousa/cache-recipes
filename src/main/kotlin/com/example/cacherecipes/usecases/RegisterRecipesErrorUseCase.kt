package com.example.cacherecipes.usecases

import com.example.cacherecipes.persistence.entities.RecipesError
import com.example.cacherecipes.persistence.repositories.RecipesErrorRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class RegisterRecipesErrorUseCase(
    private val recipesError: RecipesErrorRepository
) {
    private val logger = KotlinLogging.logger {}

    fun invoke(idRecipe: Long) {
        logger.info { "[RegisterRecipesErrorUseCase] register recipe to process later: $idRecipe" }
        recipesError.save(RecipesError(idRecipe))
    }
}