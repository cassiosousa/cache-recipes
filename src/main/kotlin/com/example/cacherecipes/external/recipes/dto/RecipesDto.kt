package com.example.cacherecipes.external.recipes.dto

data class RecipesDto(
    val recipes: List<RecipesItemDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
