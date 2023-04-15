package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.domain.model.DomainModel
import com.example.simplemoviecatalog.ui.adapters.favorite.FavoritesAdapter
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    //by lazy fue sugerencia de ChatGPT, antes no lo tenia.
    private val favoritesAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(emptyList())
    }

    private val favoritesViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovies()
        binding.btnAddToFavorites.setOnClickListener { verifyFavorites() }
        binding.btnDeleteToFavorites.setOnClickListener { deleteFromFavorites() }

    }
    //Recuperamos los datos del MainActivity
    //.getParcelable esta deprecado y no se actualmente que se utiliza como reemplazo
    //el problema del adaptador puede venir de aca?
    private fun getMovies() {
        val movie = intent.extras?.getParcelable<DomainModel>("movie")
        movie?.let {
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.tvReleaseDate.text = it.releaseDate
            binding.tvVoteAverage.text = it.voteAverage
            Picasso.get().load(Constants.IMAGE_BASE + it.image).into(binding.ivImage)
            binding.progressBar.isVisible = false
        }
    }

    private fun verifyFavorites() {
        val movie = intent.extras?.getParcelable<DomainModel>("movie")
        movie?.let {
            favoritesViewModel.verifyFavorite(it.title)
            favoritesViewModel.verifyLiveData.observe(this) { isFavorite ->
                if (isFavorite) {
                    binding.btnAddToFavorites.isEnabled = false
                    Toast.makeText(this,"${it.title} se encuentra en favoritos.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    addToFavorites(it)
                    binding.btnAddToFavorites.isEnabled = false
                }
            }
        }
    }

    private fun addToFavorites(movie:DomainModel) {
        val movie = createMovieInstance(movie)
        favoritesViewModel.addToFavorites(movie)
        favoritesViewModel.getFavorites()
        Toast.makeText(this, "Se ha agregado a favoritos.",
            Toast.LENGTH_SHORT).show()
        finish()
    }

    //deberia eliminar y actualizar la lista de favoritos
    //el problema deberia estar aca
    private fun deleteFromFavorites() {
        //tal vez en esta variable
        //tal vez esta pasando un null
        val movie = intent.extras?.getParcelable<DomainModel>("movie")
        movie?.let {
            val favoritesMovie = createMovieInstance(movie)
            //en alguna de estas dos funciones
            favoritesViewModel.deleteFavoriteMovie(favoritesMovie)
            favoritesAdapter.removeFavorite(favoritesMovie)
            Toast.makeText(this, "Se ha eliminado de favoritos.", Toast.LENGTH_SHORT).show()
            binding.btnAddToFavorites.isEnabled = true
            finish()
        }
    }

    private fun createMovieInstance(movie: DomainModel): DomainFavoritesModel {
        return DomainFavoritesModel(
            title = movie.title,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            image = movie.image
        )
    }
}