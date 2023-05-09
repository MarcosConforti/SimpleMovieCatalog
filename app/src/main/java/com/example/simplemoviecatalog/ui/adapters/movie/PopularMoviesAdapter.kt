package com.example.simplemoviecatalog.ui.adapters.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviecatalog.R
import com.example.simplemoviecatalog.domain.model.DomainModel

class PopularMoviesAdapter(
    private var popularMoviesList: List<DomainModel>,
    private var onClickMoviesListener: OnClickMoviesListener?
) : RecyclerView.Adapter<PopularMoviesViewHolder>(), Filterable {

    var filteredPopularMoviesList: List<DomainModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PopularMoviesViewHolder(layoutInflater.inflate
            (R.layout.item_grid_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val item = filteredPopularMoviesList[position]
        holder.render(item)
        holder.itemView.setOnClickListener { onClickMoviesListener?.onMoviesClicked(item) }
    }

    override fun getItemCount(): Int = filteredPopularMoviesList.size

    fun setPopularMoviesList(newMovieList: List<DomainModel>) {
        popularMoviesList = newMovieList
        filteredPopularMoviesList = newMovieList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    popularMoviesList
                } else {
                    popularMoviesList.filter { it.title.contains(constraint, true) }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredPopularMoviesList = results?.values as List<DomainModel>?
                    ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}