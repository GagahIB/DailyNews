package id.gagahib.newsapi.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: String = "",
    @Json(name = "message")
    val message: String = "",
    @Json(name = "totalResults")
    val totalResults: Int = 0,
    @Json(name = "articles")
    val articles: List<ArticleModel> = listOf(),
    @Json(name = "sources")
    val sources: List<SourceModel> = listOf()
)