package com.example.foodhub.main.network.data


import com.google.gson.annotations.SerializedName

data class MealDB(
    @SerializedName("meals")
    val meals: List<Meal?>? = null
)