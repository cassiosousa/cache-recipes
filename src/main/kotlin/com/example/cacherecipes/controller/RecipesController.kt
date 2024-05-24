package com.example.cacherecipes.controller

import com.example.cacherecipes.openapi.GetRecipesDoc
import com.example.cacherecipes.services.RecipesService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/recipes")
class RecipesController(
    val recipeServices: RecipesService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/{id}")
    @GetRecipesDoc
    fun getRecipe(@PathVariable id: Long): ResponseEntity<*> {
        logger.info { "[RecipesController] searching recipe with id:$id" }
        return recipeServices.getRecipeById(id)
            .fold(
                {
                    logger.info { "[RecipesController] fail searching recipe with id:$id" }
                    val detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "fail searching recipe with id:$id")
                    ResponseEntity.badRequest().body(detail)
                },
                {
                    logger.info { "[RecipesController] success searching recipe with id:$id" }
                    ResponseEntity.ok(it)
                }
            )

    }


}