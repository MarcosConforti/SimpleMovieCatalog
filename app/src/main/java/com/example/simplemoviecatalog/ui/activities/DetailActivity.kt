package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.ui.adapters.favorite.FavoritesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(emptyList())
    }
    private lateinit var movie: DomainFavoritesModel

    private val favoritesViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie =  intent.extras?.getParcelable("movie")!!
        getMovies()
        binding.btnAddToFavorites.setOnClickListener { verifyFavorites() }
        binding.btnDeleteToFavorites.setOnClickListener { deleteFromFavorites() }

    }
    //Recupero los datos del MainActivity
    private fun getMovies() {
        movie.let {
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.tvReleaseDate.text = it.releaseDate
            binding.tvVoteAverage.text = it.voteAverage
            Picasso.get().load(Constants.IMAGE_BASE + it.image).into(binding.ivImage)
            binding.progressBar.isVisible = false
        }
    }
    //Verifica si la pelicula ya se encuentra en la base de datos y desabilita el boton
    private fun verifyFavorites() {
        movie.let {
            favoritesViewModel.verifyFavorite(it.title)
            favoritesViewModel.verifyLiveData.observe(this) { isFavorite ->
                if (isFavorite) {
                    binding.btnAddToFavorites.isEnabled = false
                    Toast.makeText(this,"La película ${it.title} ya está en favoritos.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    addToFavorites(it)
                    binding.btnAddToFavorites.isEnabled = false
                }
            }
        }
    }


    private fun addToFavorites(movie:DomainFavoritesModel) {
        favoritesViewModel.addToFavorites(movie)
        favoritesViewModel.getFavorites()
        Toast.makeText(this, "Se ha agregado a favoritos.",
            Toast.LENGTH_SHORT).show()
        finish()
    }

    //deberia eliminar y actualizar la lista de favoritos
    private fun deleteFromFavorites() {
        movie.let {
            //Borra una pelicula de la base de datos
            favoritesViewModel.deleteFavoriteMovie(movie)
            //Saca y actualiza el adapter...supuestamente
            favoritesAdapter.removeFavorite(movie)
            Toast.makeText(this, "Se ha eliminado de favoritos.", Toast.LENGTH_SHORT).show()
            //Al eliminar una pelicula, desabilita el boton
            binding.btnAddToFavorites.isEnabled = true
            finish()
        }
    }

}