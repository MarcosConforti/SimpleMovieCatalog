package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplemoviecatalog.databinding.ActivityFavoritesBinding
import com.example.simplemoviecatalog.ui.UIState
import com.example.simplemoviecatalog.ui.adapters.FavoritesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity(),SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityFavoritesBinding

    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(emptyList())
    }

    private val favoritesViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchView.setOnQueryTextListener(this)
        configRecycler()
        configObservers()
    }

    private fun configRecycler() {
        binding.rvFavoriteMovies.adapter = favoritesAdapter
        val manager = GridLayoutManager(this, 2)
        binding.rvFavoriteMovies.layoutManager = manager
    }

    private fun configObservers() {
       lifecycleScope.launch {
           favoritesViewModel.getFavorites()
           favoritesViewModel.favoriteUIState.collect{
               favorites ->
               when(favorites){
                   UIState.Loading -> {}//progressbar
                   is UIState.Success -> {
                       favoritesAdapter.setFavoritesList(favorites.data)
                      Toast.makeText(this@FavoritesActivity,"no se ve la lista",
                          Toast.LENGTH_SHORT).show()
                   }
                   is UIState.Error -> {
                      Toast.makeText(this@FavoritesActivity,
                          Constants.GENERIC_ERROR, Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        favoritesAdapter.filter.filter(text)
        return false
    }
}