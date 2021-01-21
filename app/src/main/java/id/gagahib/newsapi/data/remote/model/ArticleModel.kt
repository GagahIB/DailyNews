package id.gagahib.newsapi.data.remote.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class ArticleModel(
        @Json(name = "source")
        val source: SourceModel,
        @Json(name = "author")
        val author: String = "",
        @Json(name = "title")
        val title: String = "",
        @Json(name = "description")
        val description: String = "",
        @Json(name = "url")
        val url: String = "",
        @Json(name = "urlToImage")
        val urlToImage: String = "",
        @Json(name = "publishedAt")
        val publishedAt: String = "",
        @Json(name = "content")
        val content: String = ""
) : Serializable