package id.gagahib.newsapi.data.remote.service

import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.mylogin.data.remote.payload.NewsPayload
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("sources")
    suspend fun getSourcesByCategory(
        @Query("category") category: String)
        : Response<NewsResponse>


    @GET("everything")
    suspend fun getNewsBySources(
        @Query("sources")
        sources: String,
        @Query("pageSize")
        pageSize: Int,
        @Query("page")
        page: Int,
        @Query("q")
        q: String
    ): Response<NewsResponse>
}
