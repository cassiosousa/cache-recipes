package com.example.cacherecipes.usecases

import com.example.cacherecipes.Either
import com.example.cacherecipes.external.recipes.dto.RecipesItemDto
import com.example.cacherecipes.external.recipes.http.DummyJsonRecipesService
import com.example.cacherecipes.persistence.entities.Recipes
import com.example.cacherecipes.persistence.repositories.RecipesRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Component
class CacheRecipesUseCase(
    val recipeRepository: RecipesRepository,
    val recipesHttpService: DummyJsonRecipesService,
) {

    private val logger = KotlinLogging.logger {}

    @Transactional
    fun invoke(idRecipe: Long): Either<String, Recipes> {
        logger.info { "[CacheRecipesUseCase] searching recipe with id:$idRecipe" }
        return runCatching {
            findRecipeInDatabase(idRecipe) ?: run {
                parseRecipe(recipesHttpService.getRecipes(idRecipe))
                    .let {
                        logger.info { "[CacheRecipesUseCase] saving recipe with id:$idRecipe into database." }
                        recipeRepository.save(it)
                    }
            }
        }.onFailure {
            logger.error { "Failed to lookup recipe $idRecipe, ${it.message}" }
        }.fold({
            Either.right(it)
        }, {
            Either.left("Failed to lookup recipe $idRecipe, ${it.message}")
        })
    }

    private fun findRecipeInDatabase(id: Long): Recipes? =
        recipeRepository.findById(id).getOrNull()

    private fun parseRecipe(recipesItemDto: RecipesItemDto): Recipes =
        with(recipesItemDto) {
            Recipes(
                id = id,
                name = name,
                ingredients = ingredients,
                instructions = instructions,
                prepTimeMinutes = prepTimeMinutes,
                cookTimeMinutes = cookTimeMinutes,
                servings = servings,
                difficulty = difficulty,
                cuisine = cuisine,
                caloriesPerServing = caloriesPerServing,
                tags = tags,
                userId = userId,
                image = image,
                rating = rating,
                reviewCount = reviewCount,
                mealType = mealType
            )
        }

}