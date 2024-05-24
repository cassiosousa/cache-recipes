package com.example.cacherecipes.usecases

import com.example.cacherecipes.persistence.entities.RecipesError
import com.example.cacherecipes.persistence.repositories.RecipesErrorRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TryCacheRecipesUseCase(
    private val recipesErrorRepository: RecipesErrorRepository,
    private val cacheRecipesUseCase: CacheRecipesUseCase
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun invoke() = runCatching {
            recipesErrorRepository.findTop5ByStatus()
                .forEach {
                    logger.info { "[TryCacheRecipesUseCase] processing $it" }
                    recipesErrorRepository.save(it.copy(status = "PROCESSING"))
                    processingRecipe(it)
                }
        }


    private fun processingRecipe(recipe: RecipesError) =
        cacheRecipesUseCase.invoke(recipe.idRecipe)
            .fold({
                recipesErrorRepository.save(recipe.copy(status = "NEW"))
                logger.info { "[TryCacheRecipesUseCase][processingRecipe] processing fail $it" }
            }, {
                recipesErrorRepository.deleteById(recipe.idRecipe)
                logger.info { "[TryCacheRecipesUseCase][processingRecipe] processing success $it" }
            })

}