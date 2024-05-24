package com.example.cacherecipes.external.recipes.http

import com.example.cacherecipes.external.recipes.dto.RecipesDto
import com.example.cacherecipes.external.recipes.dto.RecipesItemDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    name = "dummy-recipes-client",
    qualifiers = ["dummy-recipes-client"],
    fallbackFactory = RecipesFallBackFactory::class
)
interface DummyJsonRecipesService {
    @RequestMapping("/recipes", method = [RequestMethod.GET])
    fun getRecipes(): RecipesDto

    @RequestMapping("/recipes/{id}", method = [RequestMethod.GET])
    fun getRecipes(@PathVariable id: Long): RecipesItemDto
}