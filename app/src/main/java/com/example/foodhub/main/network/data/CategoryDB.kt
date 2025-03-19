package com.example.foodhub.main.network.data


import com.google.gson.annotations.SerializedName

data class CategoryDB(
    @SerializedName("categories")
    val categories: List<Category?>? = null
)