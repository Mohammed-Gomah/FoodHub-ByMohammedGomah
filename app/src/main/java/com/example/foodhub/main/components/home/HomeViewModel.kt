package com.example.foodhub.main.components.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.main.network.remote.MainRepository
import com.example.foodhub.main.network.data.Category
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _categories = MutableLiveData<List<Category>>()
    val categories : LiveData<List<Category>> = _categories

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

    companion object{
        private const val TAG = "HomeViewModel"
    }
}