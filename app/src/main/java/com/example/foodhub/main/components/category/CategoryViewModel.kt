package com.example.foodhub.main.components.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.main.network.data.Area
import com.example.foodhub.main.network.data.Meal
import com.example.foodhub.main.network.remote.MainRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _mealByLetter = MutableLiveData<List<Meal>>()
    val mealByLetter: LiveData<List<Meal>> = _mealByLetter

    private var _mealByArea = MutableLiveData<List<Meal>>()
    val mealByArea: LiveData<List<Meal>> = _mealByArea


    private var _area = MutableLiveData<List<Area>>()
    val area: LiveData<List<Area>> = _area

    fun listMealsByLetters(firstLetter: String) {
        try {
            viewModelScope.launch {
                val response = repository.apiService.listMealByFirstLetter(firstLetter)
                if (response.isSuccessful) {
                    _mealByLetter.postValue(response.body()?.meals?.filterNotNull())
                } else {
                    Log.e(TAG, "mealsByLetters: ${response.body()} ")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun listMealsByArea(area: String) {
        try {
            viewModelScope.launch {
                val response = repository.apiService.listMealByArea(area)
                if (response.isSuccessful) {
                    _mealByArea.postValue(response.body()?.meals?.filterNotNull())
                } else {
                    _mealByArea.postValue(emptyList())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun listAllAreas(){
        try {
            viewModelScope.launch {
                val response = repository.apiService.listAllAreas("list")
                if (response.isSuccessful){
                    _area.postValue(response.body()?.meals?: emptyList())
                }else{
                    _area.postValue(emptyList())

                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "CategoryViewModel"
    }

}