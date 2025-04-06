package com.example.foodhub.main.components.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val auth = FirebaseAuth.getInstance()

    private val _user = MutableLiveData<FirebaseUser?>()
    val user : LiveData<FirebaseUser?> = _user

    init {
        _user.value = auth.currentUser
    }

    fun refreshUser(){
        _user.value = auth.currentUser
    }
}