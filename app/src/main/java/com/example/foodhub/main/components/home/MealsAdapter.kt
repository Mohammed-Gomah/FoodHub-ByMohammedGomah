package com.example.foodhub.main.components.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.databinding.MealItemBinding
import com.example.foodhub.main.network.data.Meal

class MealsAdapter(private var mealList:List<Meal>) : RecyclerView.Adapter<MealsAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding : MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meal = mealList[position]

        holder.apply {
            binding.apply {
                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivMeal)
            }
        }
    }

    fun setMeals(newMealList : List<Meal>){
        mealList = newMealList
        notifyDataSetChanged()
    }
}