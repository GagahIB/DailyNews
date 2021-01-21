package id.gagahib.newsapi.data.remote.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CountryModel(
        val name: String = "",
        val code: String = ""
) : Serializable