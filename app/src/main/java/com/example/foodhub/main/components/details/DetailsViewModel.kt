package com.example.foodhub.main.components.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.main.network.data.Meal
import com.example.foodhub.main.network.remote.MainRepository
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private var _meal = MutableLiveData<Meal>()
    val meal : LiveData<Meal> = _meal

    fun fetchMealDetails(){
        try {
            viewModelScope.launch {

            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}