package com.useless.boosterapp4

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.useless.boosterapp4.network.Movie
import kotlinx.android.synthetic.main.movie_details.view.*
import kotlinx.android.synthetic.main.movie_recycler_item.view.*
import retrofit2.Retrofit

class MovieViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val moviePicture : ImageView? = itemView.movie_poster

}