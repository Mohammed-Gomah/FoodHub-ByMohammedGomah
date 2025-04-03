package com.example.foodhub.favourite

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodhub.main.network.data.Meal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val firebaseRef =
        userId?.let { FirebaseDatabase.getInstance().getReference("favourites").child(it) }

    private var _favouritesList = MutableLiveData<List<Meal>>()
    val favouritesList: LiveData<List<Meal>> get() = _favouritesList


    fun checkIsFavourite(mealId: String, callback: (Boolean) -> Unit) {
        if (firebaseRef == null) return
        firebaseRef.child(mealId).get()
            .addOnSuccessListener { meal ->
                callback(meal.exists())
            }
    }

    fun setupFavouriteToggle(meal: Meal, updateUI: (Boolean) -> Unit) {
        if (firebaseRef == null) return
        meal.idMeal?.let {
            checkIsFavourite(it) { isFavourite ->
                if (isFavourite) {
                    firebaseRef.child(meal.idMeal.toString()).removeValue()
                        .addOnSuccessListener {
                            updateUI(false)
                        }
                } else {
                    firebaseRef.child(meal.idMeal.toString()).setValue(meal)
                        .addOnSuccessListener {
                            updateUI(true)
                        }
                }
            }
        }
    }

    fun getFavourites() {
        if (firebaseRef == null) return
        firebaseRef.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val favouriteList = mutableListOf<Meal>()
                for (child in snapshot.children){
                    val meal = child.getValue(Meal::class.java)
                    meal?.let { favouriteList.add(it) }
                }
                _favouritesList.value = favouriteList
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(getApplication(), "you don't have favourites", Toast.LENGTH_SHORT).show()
            }
        })
    }


}