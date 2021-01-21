package id.gagahib.newsapi.data.remote.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CategoryModel(
        @JvmSuppressWildcards
        val id: String = "",
        @JvmSuppressWildcards
        val name: String = "",
        @JvmSuppressWildcards
        val icon: Int
) : Serializable