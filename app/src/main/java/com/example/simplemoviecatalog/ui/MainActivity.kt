package com.example.simplemoviecatalog.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplemoviecatalog.databinding.ActivityMainBinding
import com.example.simplemoviecatalog.domain.NetworkState
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.ui.recyclerViews.OnClickMoviesListener
import com.example.simplemoviecatalog.ui.recyclerViews.PopularMoviesAdapter
import com.example.simplemoviecatalog.ui.viewModels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickMoviesListener, SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding

    //private val movieList = ArrayList<DomainModel>() //aca esta el error

    private var popularMoviesAdapter = PopularMoviesAdapter(emptyList(), this)

    private val moviesViewModel: MoviesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(this)
        binding.progressBar.isVisible = true

        configRecycler()
        configObservers()


    }

    private fun configRecycler() {
        binding.rvPopular.adapter = popularMoviesAdapter
        val manager = GridLayoutManager(this, 2)
        binding.rvPopular.layoutManager = manager

    }

    private fun configObservers() {
        moviesViewModel.getMoviesLiveData.observe(this) { movieState ->

            if (movieState is NetworkState.Success) {
                binding.progressBar.isVisible = false
                popularMoviesAdapter.setPopularMoviesList(movieState.data.popular)
            } else {
                binding.progressBar.isVisible = false
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onMoviesClicked(movie: DomainModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("image", movie.image)
        intent.putExtra("overView", movie.overview)
        intent.putExtra("title", movie.title)
        intent.putExtra("releaseDate", movie.releaseDate)
        intent.putExtra("voteAverage", movie.voteAverage)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //moviesViewModel.searchItem(query.orEmpty())
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
       // filterList(newText)
        return false
    }
    private fun searchMovie(query:String?){
        val searchQuery = "%$query%"


    }

    /*private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<DomainModel>()
            for (i in movieList) {
                if (i.title.lowercase(Locale.getDefault()).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "Error al buscar pelicula", Toast.LENGTH_SHORT).show()
            } else {
                popularMoviesAdapter.setFilteredList(filteredList)
            }
        }
    }*/
}