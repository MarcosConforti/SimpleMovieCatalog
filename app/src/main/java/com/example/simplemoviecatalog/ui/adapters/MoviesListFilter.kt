package com.example.simplemoviecatalog.ui.adapters

import android.widget.Filter
import androidx.core.text.isDigitsOnly
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.ui.adapters.movie.PopularMoviesAdapter

class MoviesListFilter(
    private val adapter: PopularMoviesAdapter
): Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filterString = constraint.toString()
        val results = FilterResults()
        val list = adapter.filteredPopularMoviesList

        val filteredList: List<DomainModel> = if (filterString.isEmpty()) {
            list
        } else {
            list.filter {
                it.title.contains(filterString, true) or (filterString.isDigitsOnly() && it.id.equals( filterString.toInt()))
            }
        }

        results.values = filteredList
        results.count = filteredList.size

        return results
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.filteredPopularMoviesList = results?.values as List<DomainModel>
        adapter.notifyDataSetChanged()
    }

}
