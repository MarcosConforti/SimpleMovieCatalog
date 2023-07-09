package com.example.simplemoviecatalog.ui.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.simplemoviecatalog.R
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.ui.model.UIModel
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var movie: UIModel

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = intent.extras?.getParcelable("movie")!!
        getMovies()
        binding.btnAddToFavorites.setOnClickListener { isChecked() }

    }

    //Recupero los datos del MainActivity
    private fun getMovies() {
        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        binding.tvReleaseDate.text = movie.releaseDate
        binding.tvVoteAverage.text = movie.voteAverage
        binding.ivBigImage.scaleType = ImageView.ScaleType.FIT_XY
        Picasso.get().load(Constants.IMAGE_BASE + movie.image).into(binding.ivBigImage)
    }

    private fun isChecked() {
        lifecycleScope.launch {
            val isFavorite = favoriteViewModel.isChecked(movie.id.toString())
            if (isFavorite) {
                removeFromFavorites(movie.id.toString())
            } else {
                addToFavorites()
            }
        }
    }

    private fun addToFavorites() {
        favoriteViewModel.addToFavorites(movie)
        binding.btnAddToFavorites.setImageResource(R.drawable.ic_favorite_red)
        Toast.makeText(this, movie.title + " " + Constants.ADD_TO_FAVORITES,
            Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun removeFromFavorites(id: String) {
        favoriteViewModel.deleteFavoriteMovie(id)
        binding.btnAddToFavorites.setImageResource(R.drawable.ic_favorite_border)
        Toast.makeText(this, Constants.REMOVE_TO_FAVORITES,
            Toast.LENGTH_SHORT).show()
        finish()
    }
}