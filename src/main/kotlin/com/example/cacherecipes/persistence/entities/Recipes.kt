package com.example.cacherecipes.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "recipes")
data class Recipes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "ingredients")
    @ElementCollection(targetClass = String::class)
    val ingredients: List<String>,
    @Column(name = "instructions")
    @ElementCollection(targetClass = String::class)
    val instructions: List<String>,
    @Column(name = "prepTimeMinutes", nullable = false, columnDefinition = "smallint")
    val prepTimeMinutes: Int,
    @Column(name = "cookTimeMinutes", nullable = false, columnDefinition = "smallint")
    val cookTimeMinutes: Int,
    @Column(name = "servings", nullable = false, columnDefinition = "smallint")
    val servings: Int,
    @Column(name = "difficulty", nullable = false)
    val difficulty: String,
    @Column(name = "cuisine", nullable = false)
    val cuisine: String,
    @Column(name = "caloriesPerServing", nullable = false)
    val caloriesPerServing: Int,
    @Column(name = "tags")
    @ElementCollection(targetClass = String::class)
    val tags: List<String>,
    @Column(name = "userId", nullable = false)
    val userId: Int,
    @Column(name = "image", nullable = false)
    val image: String,
    @Column(name = "rating", nullable = false)
    val rating: Double,
    @Column(name = "reviewCount", nullable = false)
    val reviewCount: Int,
    @Column(name = "mealType")
    @ElementCollection(targetClass = String::class)
    val mealType: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipes

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
