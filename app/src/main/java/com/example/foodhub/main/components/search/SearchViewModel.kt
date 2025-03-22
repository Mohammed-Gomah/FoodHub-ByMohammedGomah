package com.example.foodhub.main.components.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.main.network.data.Meal
import com.example.foodhub.main.network.remote.MainRepository
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun searchMealByName(mealName: String) {
        try {
            viewModelScope.launch {
                val response = repository.apiService.searchMealByName(mealName)
                if (response.isSuccessful) {
                    _meals.postValue(response.body()?.meals?.filterNotNull())
                } else {
                    Log.e(TAG, "searchMealByName: ")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }

}