package com.example.simplemoviecatalog.ui.adapters.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.R
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel

class FavoritesAdapter(
    private var favoritesList: List<DomainFavoritesModel>,
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate(R.layout.item_grid_list, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = favoritesList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = favoritesList.size

    fun setFavoritesList(newFavoriteList: List<DomainFavoritesModel>) {
        favoritesList = newFavoriteList
        notifyDataSetChanged()
    }

    fun removeFavorite(favorite: DomainFavoritesModel) {
        favoritesList.indexOf(favorite)
        val index = favoritesList.indexOf(favorite)
        if (index >= 0) {
            favoritesList = favoritesList.filter { it != favorite }
            notifyItemRemoved(index)
        }
    }

}