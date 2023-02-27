package com.example.simplemoviecatalog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.databinding.ActivityDetailBinding
import com.example.simplemoviecatalog.utils.Constants
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovies()

    }

    private fun getMovies(){
        val image = intent.getStringExtra("image").toString()
        val title = intent.getStringExtra("title").toString()
        val overView = intent.getStringExtra("overView").toString()
        val releaseDate = intent.getStringExtra("releaseDate").toString()
        val voteAverage = intent.getStringExtra("voteAverage").toString()

        binding.tvTitle.text = title
        binding.tvOverview.text = overView
        binding.tvReleaseDate.text = releaseDate
        binding.tvVoteAverage.text = voteAverage
        Picasso.get().load(Constants.IMAGE_BASE + image).into(binding.ivImage)
        binding.progressBar.isVisible = false
    }
}