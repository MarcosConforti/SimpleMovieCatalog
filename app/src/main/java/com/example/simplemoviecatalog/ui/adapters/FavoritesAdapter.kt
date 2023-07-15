package com.example.simplemoviecatalog.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.R
import com.example.simplemoviecatalog.ui.model.UIModel

class FavoritesAdapter(
    private var favoritesList: List<UIModel>
) : RecyclerView.Adapter<BaseViewHolder>(), Filterable {

    var filteredFavoriteList: List<UIModel> = favoritesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(layoutInflater.inflate
            (R.layout.item_grid_list, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = filteredFavoriteList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = filteredFavoriteList.size

    fun setFavoritesList(newFavoriteList: List<UIModel>) {
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
                filteredFavoriteList = results?.values as List<UIModel>?
                    ?: emptyList()
                favoritesList = filteredFavoriteList  // Agrega esta línea para mantener la lista completa
                notifyDataSetChanged()
            }
        }
    }
}