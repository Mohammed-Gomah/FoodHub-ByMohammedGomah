package com.example.foodhub.main.components.category.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.databinding.LetterItemBinding
import com.example.foodhub.main.components.category.CategoryViewModel

class LettersAdapter(private val letterList: List<Char> , private val categoryViewModel: CategoryViewModel) :
    RecyclerView.Adapter<LettersAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: LetterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LetterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letterList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val letter = letterList[position]

        holder.apply {
            binding.apply {
                tvLetter.text = letter.toString()

                letterLayerBg.setBackgroundResource(
                    if (position == selectedPosition) R.drawable.orange_curve_bg else R.drawable.orange_stroke_bg
                )

                tvLetter.setTextColor(
                    if (position == selectedPosition) ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    ) else ContextCompat.getColor(itemView.context, R.color.primary_color)
                )

                letterLayerBg.setOnClickListener {
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = holder.adapterPosition
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedPosition)
                    categoryViewModel.listMealsByLetters(letter.toString())
                }

            }
        }

    }
}