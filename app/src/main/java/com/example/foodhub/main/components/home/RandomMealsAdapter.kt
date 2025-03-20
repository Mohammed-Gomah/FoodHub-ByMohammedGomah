package com.example.foodhub.main.components.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.databinding.RandomItemBinding
import com.example.foodhub.main.network.data.Meal

class RandomMealsAdapter(private var randomMealList: List<Meal>) :
    RecyclerView.Adapter<RandomMealsAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: RandomItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = RandomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return randomMealList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meal = randomMealList[position]
        holder.apply {
            binding.apply {
                tvMeal.text = meal.strMeal
                tvMealCategory.text = meal.strCategory
                tvArea.text = meal.strArea

                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivMeal)
            }
        }
    }

    fun setMeals(newRandomMeal : List<Meal>){
        randomMealList = newRandomMeal
        notifyDataSetChanged()
    }
}