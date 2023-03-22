package com.example.simplemoviecatalog.ui.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.R
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities

class FavoritesAdapter(
    private var favoritesList: List<FavoritesEntities>,
    private var onClickFavoritesListener: OnClickFavoritesListener?
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate(R.layout.item_grid_list, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = favoritesList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onClickFavoritesListener?.onFavoritesClicked(item) }
    }

    override fun getItemCount(): Int = favoritesList.size

    fun setFavoritesList(newFavoriteList: List<FavoritesEntities>) {
        favoritesList = newFavoriteList
        notifyDataSetChanged()
    }

}