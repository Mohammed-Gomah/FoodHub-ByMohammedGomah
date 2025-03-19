package com.example.foodhub.main.components.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.databinding.CategoryItemBinding
import com.example.foodhub.main.network.data.Category

class CategoryAdapter(private var categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val category = categoryList[position]
        holder.apply {
            binding.apply {
                categoryName.text = category.strCategory

                Glide.with(itemView.context)
                    .load(category.strCategoryThumb)
                    .into(ivCategory)
            }
        }
    }

    fun setCategories(newCategoriesList: List<Category>) {
        categoryList = newCategoriesList
        notifyDataSetChanged()
    }
}