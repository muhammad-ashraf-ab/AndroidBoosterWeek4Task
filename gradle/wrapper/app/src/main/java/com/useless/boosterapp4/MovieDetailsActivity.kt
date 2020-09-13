package com.useless.boosterapp4

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_recycler_item.*
import kotlinx.android.synthetic.main.movie_recycler_item.view.*

const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val Logo_Path = "extra_logo_path"
class MovieDetailsActivity: AppCompatActivity() {
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var backdrop: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        backdrop = findViewById(R.id.logo)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
    val extras = intent.extras

    if (extras != null) {
        populateDetails(extras)
    } else {
        finish()
    }}

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }


    title.text = extras.getString(MOVIE_TITLE, "")
    overview.text = extras.getString(MOVIE_OVERVIEW, "")
}
}