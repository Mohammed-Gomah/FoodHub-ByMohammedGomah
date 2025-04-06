package com.example.foodhub.main.components.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.databinding.IngredientItemBinding
import com.example.foodhub.main.network.data.Meal

class DetailsAdapter(private val ingredientList: List<String>) :
    RecyclerView.Adapter<DetailsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.apply {
            binding.apply {
                tvIngredient.text = ingredient
            }
        }
    }
}