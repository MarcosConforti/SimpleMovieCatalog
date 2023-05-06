package com.example.simplemoviecatalog.ui.adapters.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.R
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.domain.model.DomainModel

class FavoritesAdapter(
    private var favoritesList: List<DomainFavoritesModel>
) : RecyclerView.Adapter<FavoritesViewHolder>(), Filterable {

    var filteredFavoriteList: List<DomainFavoritesModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate
            (R.layout.item_grid_list, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = filteredFavoriteList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = filteredFavoriteList.size

    fun setFavoritesList(newFavoriteList: List<DomainFavoritesModel>) {
        favoritesList = newFavoriteList
        filteredFavoriteList = newFavoriteList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    favoritesList
                } else {
                    favoritesList.filter { it.title.contains(constraint, true) }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredFavoriteList = results?.values as List<DomainFavoritesModel>?
                    ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}