package com.example.foodhub.main.components.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.databinding.SearchItemBinding
import com.example.foodhub.main.network.data.Meal

class SearchAdapter(private var searchList: List<Meal>) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meal = searchList[position]
        holder.apply {
            binding.apply {
                tvMeal.text = meal.strMeal
                tvArea.text = meal.strArea
                tvMealCategory.text = meal.strCategory

                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivMeal)
            }
        }
    }

    fun setSearchList(newSearchList: List<Meal>) {
        searchList = newSearchList
        notifyDataSetChanged()
    }
}