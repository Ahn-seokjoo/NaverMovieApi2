package com.example.mvvmex.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResult(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("lastBuildDate")
    val lastBuildDate: String,
    @SerializedName("start")
    val start: Int,
    @SerializedName("total")
    val total: Int
) : Parcelable {
    @Parcelize
    @Entity(tableName = "movie")
    data class Item(
        @SerializedName("actor")
        val actor: String,
        @SerializedName("director")
        val director: String,
        @SerializedName("image")
        val image: String,
        @PrimaryKey
        @SerializedName("link")
        val link: String,
        @SerializedName("pubDate")
        val pubDate: String,
        @SerializedName("subtitle")
        val subtitle: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("userRating")
        val userRating: String,
    ) : Parcelable
}
