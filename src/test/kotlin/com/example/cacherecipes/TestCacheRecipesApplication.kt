package com.example.cacherecipes

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestCacheRecipesApplication {

//	@Bean
//	@ServiceConnection
//	fun postgresContainer(): PostgreSQLContainer<*> {
//		return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
//			.withPassword("postgres")
//			.withUsername("postgres")
//			.withDatabaseName("cache-recipes-db")
//	}

}

fun main(args: Array<String>) {
	fromApplication<CacheRecipesApplication>().with(TestCacheRecipesApplication::class).run(*args)
}
