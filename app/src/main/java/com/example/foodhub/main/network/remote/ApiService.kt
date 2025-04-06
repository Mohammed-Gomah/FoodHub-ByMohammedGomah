package com.example.foodhub.main.network.remote

import com.example.foodhub.main.network.data.AreaDB
import com.example.foodhub.main.network.data.CategoryDB
import com.example.foodhub.main.network.data.MealDB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun fetchCategories(): Response<CategoryDB>

    @GET("filter.php")
    suspend fun fetchMealsByCategory(@Query("c") categoryName: String): Response<MealDB>

    @GET("random.php")
    suspend fun fetchRandomMeal(): Response<MealDB>

    @GET("lookup.php")
    suspend fun fetchMealDetailsById(@Query("i") mealId: String): Response<MealDB>

    @GET("search.php")
    suspend fun searchMealByName(@Query("s") mealName : String) : Response<MealDB>

    @GET("search.php")
    suspend fun listMealByFirstLetter(@Query("f") firstLetter: String): Response<MealDB>

    @GET("filter.php")
    suspend fun listMealByArea(@Query("a") area: String): Response<MealDB>

    @GET("list.php")
    suspend fun listAllAreas(@Query("a") area: String): Response<AreaDB>

}