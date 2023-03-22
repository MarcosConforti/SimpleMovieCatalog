package com.example.simplemoviecatalog.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.data.database.entities.FavoritesEntities
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.domain.model.DomainFavoritesModel
import com.example.simplemoviecatalog.ui.viewModels.FavoriteViewModel
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val favoritesViewModel: FavoriteViewModel by viewModels()

    var image: String = ""
    var title: String = ""
    var overView: String = ""
    var releaseDate: String = ""
    var voteAverage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovies()
        addToFavorites()

    }

    private fun getMovies() {
        image = intent.getStringExtra("image").toString()
        title = intent.getStringExtra("title").toString()
        overView = intent.getStringExtra("overView").toString()
        releaseDate = intent.getStringExtra("releaseDate").toString()
        voteAverage = intent.getStringExtra("voteAverage").toString()

        binding.tvTitle.text = title
        binding.tvOverview.text = overView
        binding.tvReleaseDate.text = releaseDate
        binding.tvVoteAverage.text = voteAverage
        Picasso.get().load(Constants.IMAGE_BASE + image).into(binding.ivImage)
        binding.progressBar.isVisible = false
    }

    private fun addToFavorites() {
        binding.btnAddToFavorites.setOnClickListener {
            favoritesViewModel.addToFavorites(
                FavoritesEntities(
                    title = title,
                    overview = overView,
                    releaseDate = releaseDate,
                    voteAverage = voteAverage,
                    image = image
                )
            )
            Toast.makeText(this, "se ha agregado a favoritos", Toast.LENGTH_SHORT).show()
        }
    }
}