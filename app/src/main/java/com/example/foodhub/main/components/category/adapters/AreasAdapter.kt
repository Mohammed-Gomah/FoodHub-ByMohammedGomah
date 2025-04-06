package com.example.foodhub.main.components.category.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.databinding.AreaItemBinding
import com.example.foodhub.main.components.category.CategoryViewModel
import com.example.foodhub.main.network.data.Area

class AreasAdapter(private var areaList: List<Area> , private val categoryViewModel: CategoryViewModel) :
    RecyclerView.Adapter<AreasAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: AreaItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = AreaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return areaList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val area = areaList[position]

        holder.apply {
            binding.apply {
                tvArea.text = area.strArea

                areaLayerBg.setBackgroundResource(
                    if (selectedPosition == position) R.drawable.orange_curve_bg else R.drawable.orange_stroke_bg
                )

                tvArea.setTextColor(
                    if (selectedPosition == position) ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    ) else ContextCompat.getColor(itemView.context, R.color.primary_color)
                )

                areaLayerBg.setOnClickListener {
                    val previousSelected = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                    notifyItemChanged(previousSelected)
                    categoryViewModel.listMealsByArea(area.strArea?:"American")
                }

            }
        }

    }

    fun setAreas(newAreaList: List<Area>) {
        areaList = newAreaList
        notifyDataSetChanged()
    }
}