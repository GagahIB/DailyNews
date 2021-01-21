package id.gagahib.mylogin.data.remote.payload

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsPayload(
    @JvmSuppressWildcards
    @SerializedName("category")
    val category: String = "",
    @JvmSuppressWildcards
    @SerializedName("pageSize")
    val pageSize: Int = 10,
    @JvmSuppressWildcards
    @SerializedName("page")
    val page: Int = 1,
    @JvmSuppressWildcards
    @SerializedName("q")
    val q: String = "",
    @JvmSuppressWildcards
    @SerializedName("sources")
    val sources: String = ""

) {
    companion object {
        fun generate(
            sources: String,
            pageSize: Int,
            page: Int,
            q: String
        ): NewsPayload {
            return NewsPayload(
                sources = sources,
                pageSize = pageSize,
                page = page,
                q = q
            )
        }
    }
}