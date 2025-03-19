package com.example.foodhub.main.components.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.network.MainRepository
import com.example.network.data.Category
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _categories = MutableLiveData<List<Category>>()
    val categories : LiveData<List<Category>> = _categories

    fun fetchCategories(){
        try {
            viewModelScope.launch {
                val response = repository.
            }
        }
    }
}