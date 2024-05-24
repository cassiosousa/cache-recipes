package com.example.cacherecipes.usecases

import com.example.cacherecipes.AbstractPostgresTest
import com.example.cacherecipes.Either
import com.example.cacherecipes.external.recipes.dto.RecipesItemDto
import com.example.cacherecipes.external.recipes.http.DummyJsonRecipesService
import com.example.cacherecipes.persistence.entities.Recipes
import com.example.cacherecipes.persistence.repositories.RecipesRepository
import com.example.cacherecipes.recipeChocolateChipCookies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.http.HttpTimeoutException

@SpringBootTest
@AutoConfiguration
@ExtendWith(SpringExtension::class)
internal class CacheRecipesUseCaseTest : AbstractPostgresTest() {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var recipesRepository: RecipesRepository

    @MockkBean
    lateinit var recipesHttpService: DummyJsonRecipesService

    @Autowired
    lateinit var cacheRecipesUseCase: CacheRecipesUseCase


    @Test
    fun `postgresContainer is running`() {
        assertTrue(postgresContainer.isRunning)
    }

    @Test
    fun `should be found a recipes if exists in database`() {
        val recipe = mapper.readValue<Recipes>(recipeChocolateChipCookies)
        recipesRepository.deleteAll()
        val insertedRecipes = recipesRepository.save(recipe)
        assertNotNull(insertedRecipes)

        val result = cacheRecipesUseCase.invoke(insertedRecipes.id)
        assertTrue { result is Either.Right }
        assertEquals(insertedRecipes.name, result.fold({}, { it.name }))
    }

    @Test
    fun `should be cache a recipes if not exists in database`() {
        every {
            recipesHttpService.getRecipes(50)
        } returns mapper.readValue<RecipesItemDto>(recipeChocolateChipCookies).copy(userId = 99)

        val result = cacheRecipesUseCase.invoke(50)

        verify(exactly = 1) { recipesHttpService.getRecipes(50) }

        when (result) {
            is Either.Right -> {
                assertEquals("Chocolate Chip Cookies", result.right.name)
                assertEquals(99, result.right.userId)
            }
            else -> {
                fail("Either recipe is left and it should be right")
            }
        }
    }

    @Test
    fun `should be return a error if no reach service `() {
        val recipeId = 50L
        val exceptionMessage = "HTTP Timeout message"
        every {
            recipesHttpService.getRecipes(recipeId)
        } throws HttpTimeoutException(exceptionMessage)

        val result = cacheRecipesUseCase.invoke(recipeId)

        verify(exactly = 1) { recipesHttpService.getRecipes(recipeId) }

        when (result) {
            is Either.Left -> assertEquals("Failed to lookup recipe $recipeId, $exceptionMessage", result.left)

            else -> fail("Either recipe is left and it should be right")
        }
    }

}