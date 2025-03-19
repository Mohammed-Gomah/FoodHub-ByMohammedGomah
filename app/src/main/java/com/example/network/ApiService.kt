package com.example.network

import com.example.network.data.CategoryDB
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    suspend fun fetchCategories(): Response<CategoryDB>
}