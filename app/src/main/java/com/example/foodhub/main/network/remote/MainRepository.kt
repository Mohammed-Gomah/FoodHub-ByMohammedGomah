package com.example.foodhub.main.network.remote

class MainRepository {
    val apiService : ApiService by lazy {
        RetrofitModule.retrofit.create(ApiService::class.java)
    }
}