package com.example.recipefinder

data class RecipeItem(
    val name: String,
    val id: Int,
    val ingredients: String,
    val summary: String,
    val imageUrl: String,
    val recipeUrl: String
)
