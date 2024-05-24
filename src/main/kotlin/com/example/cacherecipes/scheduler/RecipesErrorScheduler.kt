package com.example.cacherecipes.scheduler

import com.example.cacherecipes.usecases.TryCacheRecipesUseCase
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
private class RecipesErrorScheduler(
    private val tryCacheRecipesUseCase: TryCacheRecipesUseCase
) {
    private val logger = KotlinLogging.logger {}

    @Scheduled(initialDelay = 2000, fixedDelay = 60000)
    fun process(){
        logger.info { "[RecipesErrorScheduler] init ${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}" }
        tryCacheRecipesUseCase.invoke()
    }
}