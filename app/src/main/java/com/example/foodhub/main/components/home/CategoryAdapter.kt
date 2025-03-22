package com.example.foodhub.main.components.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.CategoryItemBinding
import com.example.foodhub.main.network.data.Category

class CategoryAdapter(
    private var categoryList: List<Category>,
    private val homeViewModel: HomeViewModel
) :
    RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {

    private var selectedPosition = 0

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

                categoryNameBG.setBackgroundResource(
                    if (position == selectedPosition) R.drawable.orange_curve_bg else R.drawable.orange_stroke_bg16
                )

                categoryImageBG.setBackgroundResource(
                    if (position == selectedPosition) R.drawable.orange_stroke_bg16 else R.drawable.curve_bg
                )
                categoryName.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        if (position == selectedPosition) R.color.black else R.color.primary_color
                    )
                )

                categoryItem.setOnClickListener {
                    val previousSelected = selectedPosition
                    selectedPosition = holder.adapterPosition // تحديث الموقع المحدد
                    notifyItemChanged(previousSelected)
                    notifyItemChanged(selectedPosition)
                    homeViewModel.fetchMealsByCategory(category.strCategory ?: "Beef")
                }



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