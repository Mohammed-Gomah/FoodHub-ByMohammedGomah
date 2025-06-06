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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

                   val mealList =  response.body()?.meals?.filterNotNull() ?: emptyList()
                    val updatedMeals = mealList.map { meal ->
                        async {
                            val deferredMeal =
                                async { meal.idMeal?.let { fetchMealDetailsById(it) } }.await()

                            meal.copy(
                                strArea = deferredMeal?.strArea,
                                strCategory = deferredMeal?.strCategory,
                                strYoutube = deferredMeal?.strYoutube,
                                strInstructions = deferredMeal?.strInstructions,
                                strIngredient1 = deferredMeal?.strIngredient1,
                                strIngredient2 = deferredMeal?.strIngredient2,
                                strIngredient3 = deferredMeal?.strIngredient3,
                                strIngredient4 = deferredMeal?.strIngredient4,
                                strIngredient5 = deferredMeal?.strIngredient5,
                                strIngredient6 = deferredMeal?.strIngredient6,
                                strIngredient7 = deferredMeal?.strIngredient7,
                                strIngredient8 = deferredMeal?.strIngredient8,
                                strIngredient9 = deferredMeal?.strIngredient9,
                                strMeasure1 = deferredMeal?.strMeasure1,
                                strMeasure2 = deferredMeal?.strMeasure2,
                                strMeasure3 = deferredMeal?.strMeasure3,
                                strMeasure4 = deferredMeal?.strMeasure4,
                                strMeasure5 = deferredMeal?.strMeasure5,
                                strMeasure6 = deferredMeal?.strMeasure6,
                                strMeasure7 = deferredMeal?.strMeasure7,
                                strMeasure8 = deferredMeal?.strMeasure8,
                                strMeasure9 = deferredMeal?.strMeasure9,

                            )
                        }
                    }.awaitAll()
                    _meals.postValue(updatedMeals)

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
                        val randomResponse =
                            response.body()?.meals?.random()
                        randomResponse?.let { it1 -> randomMealList.add(it1) }
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

    private suspend fun fetchMealDetailsById(mealId: String): Meal? {
        return try {
            val response = repository.apiService.fetchMealDetailsById(mealId)
            if (response.isSuccessful) {
                response.body()?.meals?.filterNotNull()?.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }
}