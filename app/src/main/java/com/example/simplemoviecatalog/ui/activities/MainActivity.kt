package com.example.simplemoviecatalog.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplemoviecatalog.databinding.ActivityMainBinding
import com.example.simplemoviecatalog.ui.UIState
import com.example.simplemoviecatalog.ui.adapters.OnClickMoviesListener
import com.example.simplemoviecatalog.ui.adapters.PopularMoviesAdapter
import com.example.simplemoviecatalog.ui.model.UIModel
import com.example.simplemoviecatalog.ui.viewModels.MoviesViewModel
import com.example.simplemoviecatalog.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickMoviesListener, SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding

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
        onClickFavorites()
    }

    private fun configRecycler() {
        binding.rvPopular.adapter = popularMoviesAdapter
        val manager = GridLayoutManager(this, 2)
        binding.rvPopular.layoutManager = manager

    }

    private fun configObservers() {
        lifecycleScope.launch {
            moviesViewModel.getMoviesStateFlow.collect{movieState->
                when(movieState){
                    UIState.Loading ->{
                        binding.progressBar.isVisible = true
                    }
                    is UIState.Success -> {
                        binding.progressBar.isVisible = false
                        popularMoviesAdapter.setPopularMoviesList(movieState.data.popular)
                    }
                    is UIState.Error ->{
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@MainActivity, Constants.GENERIC_ERROR,
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        popularMoviesAdapter.filter.filter(newText)
        return false
    }

    override fun onMoviesClicked(movie: UIModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie",movie)
        startActivity(intent)
    }

    private fun onClickFavorites() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}