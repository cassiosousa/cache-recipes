package com.example.cacherecipes.external.recipes.http

import com.example.cacherecipes.external.recipes.dto.RecipesDto
import com.example.cacherecipes.external.recipes.dto.RecipesItemDto
import com.example.cacherecipes.usecases.RegisterRecipesErrorUseCase
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class RecipesFallBackFactory(

) : FallbackFactory<RecipesFallBack> {
    private val logger = KotlinLogging.logger {}
    @Autowired
    private lateinit var registerRecipesErrorUseCase: RegisterRecipesErrorUseCase
    override fun create(cause: Throwable?): RecipesFallBack {
        logger.error(cause, { "[RecipesFallBack] Error while creating recipe ${cause?.message}" })
        return RecipesFallBack(registerRecipesErrorUseCase)
    }
}

class RecipesFallBack (
    private val registerRecipesErrorUseCase: RegisterRecipesErrorUseCase
): DummyJsonRecipesService {
    private val logger = KotlinLogging.logger {}
    override fun getRecipes(): RecipesDto {
       logger.error { "[RecipesFallBack][getRecipes] error find recipes" }
        throw NoFallbackAvailableException("[RecipesFallBack][getRecipes] error find recipes",Exception())
    }

    override fun getRecipes(id: Long): RecipesItemDto {
        val message = "[RecipesFallBack][getRecipes($id)] error find recipe $id register to process later,"
        logger.warn { message }
        registerRecipesErrorUseCase.invoke(id)
        throw Exception(message)
    }

}