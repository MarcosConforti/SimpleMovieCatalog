package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var movie: DomainFavoritesModel

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = intent.extras?.getParcelable("movie")!!
        getMovies()
        binding.btnAddToFavorites.setOnClickListener { isChecked() }
        binding.btnDeleteToFavorites.setOnClickListener { deleteFromFavorites(movie) }

    }

    //Recupero los datos del MainActivity
    private fun getMovies() {
        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        binding.tvReleaseDate.text = movie.releaseDate
        binding.tvVoteAverage.text = movie.voteAverage
        Picasso.get().load(Constants.IMAGE_BASE + movie.image).into(binding.ivImage)
        binding.progressBar.isVisible = false

    }

    //Verifica si la pelicula ya se encuentra en la base de datos y desabilita el boton
    private fun isChecked() {
        favoriteViewModel.isChecked(movie.title)
        favoriteViewModel.verifyLiveData.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.btnAddToFavorites.isEnabled = false
                Toast.makeText(
                    this, "La película ${movie.title} ya está en favoritos.",
                    Toast.LENGTH_SHORT).show()
            } else {
                addToFavorites()
                finish()
                binding.btnAddToFavorites.isEnabled = true //establece en verdadero al agregar una pelicula
            }
        }
    }

    private fun addToFavorites() {
        favoriteViewModel.addToFavorites(movie)
        Toast.makeText(this, "Se ha agregado a favoritos.",
            Toast.LENGTH_SHORT).show()

    }

    //deberia eliminar y actualizar la lista de favoritos
    private fun deleteFromFavorites(movie: DomainFavoritesModel) {
        favoriteViewModel.getFavorites()
        favoriteViewModel.favoriteLiveData.observe(this) { favoritesList ->
            val movieInFavorites = favoritesList.find { it.title == movie.title }
            if (movieInFavorites != null) {
                favoriteViewModel.deleteFavoriteMovie(movieInFavorites)
                Toast.makeText(this, "Se ha eliminado de favoritos.", Toast.LENGTH_SHORT).show()
                binding.btnAddToFavorites.isEnabled = true
            } else {
                Toast.makeText(
                    this, "La película ${movie.title} no se encuentra en favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        finish()
    }
}


