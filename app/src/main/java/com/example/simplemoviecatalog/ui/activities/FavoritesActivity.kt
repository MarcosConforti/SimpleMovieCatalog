package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplemoviecatalog.databinding.ActivityFavoritesBinding
import com.example.simplemoviecatalog.ui.adapters.favorite.FavoritesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_favorites.*

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(emptyList())
    }

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
        //Traigo los favoritos
        favoritesViewModel.getFavorites()
        //Observo los cambios en la lista y seteo el progressBar
        favoritesViewModel.favoriteLiveData.observe(this, Observer { favorites ->
            if (favorites.isNotEmpty()) {
                progressBar.isVisible = false
                favoritesAdapter.setFavoritesList(favorites)
            } else {
                progressBar.isVisible = true
                favoritesAdapter.setFavoritesList(emptyList())
                Toast.makeText(this, "no se actualizo la lista", Toast.LENGTH_SHORT).show()
            }
        })
    }
}