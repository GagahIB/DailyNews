package id.gagahib.newsapi.data.remote.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SourceModel(
        @Json(name = "id")
        val id: String = "",
        @Json(name = "name")
        val name: String = "",
        @Json(name = "description")
        val description: String = "",
        @Json(name = "url")
        val url: String = "",
        @Json(name = "category")
        val category: String = "",
        @Json(name = "language")
        val language: String = "",
        @Json(name = "country")
        val country: String = ""
) : Serializable