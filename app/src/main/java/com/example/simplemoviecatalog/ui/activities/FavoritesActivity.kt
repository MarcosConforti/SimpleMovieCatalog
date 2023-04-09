package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplemoviecatalog.databinding.ActivityFavoritesBinding
import com.example.simplemoviecatalog.ui.adapters.favorite.FavoritesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    private var favoritesAdapter = FavoritesAdapter(emptyList())

    private val favoritesViewModel: FavoriteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecycler()
        configObservers()
    }

    private fun configRecycler() {
        binding.rvFavoriteMovies.adapter = favoritesAdapter
        val manager = GridLayoutManager(this, 2)
        binding.rvFavoriteMovies.layoutManager = manager

    }

    private fun configObservers() {
        favoritesViewModel.getFavorites()
        favoritesViewModel.favoriteLiveData.observe(this, Observer { favorites ->
            if (favorites.isNotEmpty()) {
                favoritesAdapter.setFavoritesList(favorites)
            } else {
                favoritesAdapter.setFavoritesList(emptyList())
            }
        })

    }
}