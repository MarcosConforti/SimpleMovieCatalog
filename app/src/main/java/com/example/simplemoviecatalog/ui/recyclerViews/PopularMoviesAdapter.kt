package com.example.simplemoviecatalog.ui.recyclerViews

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

    private val filter = MoviesListFilter(this)
    var filteredPopularMoviesList: List<DomainModel> = emptyList()

    init {
        filteredPopularMoviesList = popularMoviesList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PopularMoviesViewHolder(
            layoutInflater.inflate(
                R.layout.item_grid_list,
                parent,
                false
            )
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

    override fun getFilter(): Filter = filter

}