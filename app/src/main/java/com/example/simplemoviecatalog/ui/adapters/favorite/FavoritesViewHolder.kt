package com.example.simplemoviecatalog.ui.adapters.favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.databinding.ItemGridListBinding
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso

class FavoritesViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemGridListBinding.bind(view)

    fun render(listModel: DomainFavoritesModel){
        binding.tvTitle.text = listModel.title
        binding.tvVoteAverage.text = listModel.voteAverage
        Picasso.get().load(Constants.IMAGE_BASE + listModel.image).into(binding.ivImage)

    }
}