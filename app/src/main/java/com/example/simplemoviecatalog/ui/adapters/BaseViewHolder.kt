package com.example.simplemoviecatalog.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.databinding.ItemGridListBinding
import com.example.simplemoviecatalog.ui.model.UIModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso

class BaseViewHolder(view: View):RecyclerView.ViewHolder(view) {

   private val binding = ItemGridListBinding.bind(view)

    fun render(listModel : UIModel){
        binding.tvTitle.text = listModel.title
        binding.tvVoteAverage.text = listModel.voteAverage
        Picasso.get().load(Constants.IMAGE_BASE + listModel.image).into(binding.ivImage)
    }
}