package com.example.foodhub.main.components.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.main.network.data.Category
import com.example.foodhub.main.network.data.Meal
import com.example.foodhub.main.network.remote.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _categories = MutableLiveData<List<Category>>()
    val categories : LiveData<List<Category>> = _categories

    private var _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    private var _randomMeals = MutableLiveData<List<Meal>>()
    val randomMeals: LiveData<List<Meal>> = _randomMeals

    fun fetchCategories(){
        try {
            viewModelScope.launch {
                val response = repository.apiService.fetchCategories()
                if (response.isSuccessful){
                    _categories.postValue(response.body()?.categories?.filterNotNull() ?: emptyList())
                }else{
                    Log.e(TAG, "fetchCategories: ${response.body()} ", )
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "fetchCategories: ${e.message}")
        }
    }

    fun fetchMealsByCategory(categoryName: String) {
        try {
            viewModelScope.launch {
                val response = repository.apiService.fetchMealsByCategory(categoryName)
                if (response.isSuccessful) {
                    _meals.postValue(response.body()?.meals?.filterNotNull() ?: emptyList())
                } else {
                    Log.e(TAG, "fetchMealsByCategory: ${response.body()}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchMealsByCategory: ${e.message}")
        }
    }

    fun fetchRandomMeals() {
        try {
            viewModelScope.launch {
                val randomMealList = mutableListOf<Meal>()
                repeat(10) {
                    val response = repository.apiService.fetchRandomMeal()
                    if (response.isSuccessful) {
                        response.body()?.meals?.random()?.let { it1 -> randomMealList.add(it1) }
                    } else {
                        Log.e(TAG, "fetchRandomMeals: ${response.body()}")
                    }
                }
                _randomMeals.postValue(randomMealList)
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchRandomMeals: ${e.message}")
        }
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }
}