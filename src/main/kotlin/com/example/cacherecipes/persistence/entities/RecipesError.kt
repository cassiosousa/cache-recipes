package com.example.cacherecipes.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime
@Entity
@Table(name = "recipes_error")
data class RecipesError(
    @Id
    val idRecipe: Long,
    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "status", nullable = false)
    val status: String = "NEW",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipesError

        return idRecipe == other.idRecipe
    }

    override fun hashCode(): Int {
        return idRecipe.toInt()
    }
}
