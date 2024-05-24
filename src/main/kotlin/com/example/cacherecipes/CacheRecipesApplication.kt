package com.example.cacherecipes

import com.example.cacherecipes.external.recipes.http.DummyJsonRecipesService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
class CacheRecipesApplication

fun main(args: Array<String>) {
	runApplication<CacheRecipesApplication>(*args)
}

@Component
class TestFeign(
	val recipesService: DummyJsonRecipesService
): CommandLineRunner{
	override fun run(vararg args: String?) {
//		println(recipesService.getRecipes())
	}
}