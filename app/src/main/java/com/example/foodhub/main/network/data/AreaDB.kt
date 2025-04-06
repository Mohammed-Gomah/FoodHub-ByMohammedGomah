package com.example.foodhub.main.network.data


import com.google.gson.annotations.SerializedName

data class AreaDB(
    @SerializedName("meals")
    val meals: List<Area>? = listOf()
)