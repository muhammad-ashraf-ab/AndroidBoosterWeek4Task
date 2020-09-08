package com.useless.boosterapp4

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class movielistentity (
    @PrimaryKey
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("original_language")
    val lang: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val date: String,
    @SerializedName("vote_average")
    val voteAvg: Number,
    @SerializedName("vote_count")
    val voteCnt: Int,
    @SerializedName("overview")
    val overview : String,
    val totalPages: Int,
    @SerializedName("page")
    val page: Int
)
