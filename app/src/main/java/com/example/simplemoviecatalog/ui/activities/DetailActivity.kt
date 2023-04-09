package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.ui.adapters.favorite.FavoritesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

object FavoritesList {
    var favorites: List<FavoritesEntities> = emptyList()
}

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var favoritesAdapter = FavoritesAdapter(emptyList())

    private val favoritesViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovies()
        addToFavorites()
        deleteToFavorites()
    }

    private fun getMovies() {
        val movie = intent.getParcelableExtra<DomainModel>("movie")
        movie?.let {
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.tvReleaseDate.text = it.releaseDate
            binding.tvVoteAverage.text = it.voteAverage
            Picasso.get().load(Constants.IMAGE_BASE + it.image).into(binding.ivImage)
            binding.progressBar.isVisible = false
        }
    }

    private fun addToFavorites() {
        binding.btnAddToFavorites.setOnClickListener {
            val movie = createMovieInstance()
            favoritesViewModel.addToFavorites(movie)
            FavoritesList.favorites = favoritesViewModel.getFavorites()
            Toast.makeText(this, "se ha agregado a favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    //deberia eliminar y actualizar la lista de favoritos
    private fun deleteToFavorites() {
        binding.btnDeleteToFavorites.setOnClickListener {
            val movie = createMovieInstance()
            favoritesViewModel.deleteFavoriteMovie(movie)
            favoritesAdapter.removeFavorite(movie)
            favoritesAdapter.notifyDataSetChanged()
            Toast.makeText(this, "se ha eliminado de favoritos", Toast.LENGTH_SHORT).show()
            favoritesViewModel.getFavorites()
        }
    }

    private fun createMovieInstance(): FavoritesEntities {
        val movie = intent.getParcelableExtra<DomainModel>("movie")
        return FavoritesEntities(
            title = movie?.title.orEmpty(),
            overview = movie?.overview.orEmpty(),
            releaseDate = movie?.releaseDate.orEmpty(),
            voteAverage = movie?.voteAverage.orEmpty(),
            image = movie?.image.orEmpty()
        )
    }
}