package com.example.simplemoviecatalog.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.databinding.ActivityFavoritesBinding
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.ui.recyclerViews.FavoritesAdapter
import com.example.simplemoviecatalog.ui.recyclerViews.OnClickFavoritesListener
import com.example.simplemoviecatalog.ui.recyclerViews.PopularMoviesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity(), OnClickFavoritesListener {

    private lateinit var binding: ActivityFavoritesBinding

    private var favoritesAdapter = FavoritesAdapter(emptyList(), this)

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
    private fun configObservers(){
        favoritesViewModel.getFavorites()
        favoritesViewModel.favoriteLiveData.observe(this, Observer { favorites ->
            if (favorites.isNotEmpty()) {
                favoritesAdapter.setFavoritesList(favorites)
            }
        })
    }
    override fun onFavoritesClicked(favorite: FavoritesEntities) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("image", favorite.image)
        intent.putExtra("overView", favorite.overview)
        intent.putExtra("title", favorite.title)
        intent.putExtra("releaseDate", favorite.releaseDate)
        intent.putExtra("voteAverage", favorite.voteAverage)
        startActivity(intent)
    }
}