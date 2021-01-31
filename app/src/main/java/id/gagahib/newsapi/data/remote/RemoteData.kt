package id.gagahib.newsapi.data.remote

import id.gagahib.core.utils.Network.Utils.isConnected
import id.gagahib.newsapi.NewsAPIApplication
import id.gagahib.core.data.error.Error.Companion.NETWORK_ERROR
import id.gagahib.core.data.error.Error.Companion.NO_INTERNET_CONNECTION
import id.gagahib.core.remote.Resource
import id.gagahib.newsapi.data.remote.model.NewsResponse
import id.gagahib.newsapi.data.remote.service.ApiService
import id.gagahib.newsapi.utils.wrapEspressoIdlingResource
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator) : RemoteDataSource {

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!isConnected(NewsAPIApplication.context)) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

    override suspend fun getSourcesByCategory(category: String): Resource<NewsResponse> {
        wrapEspressoIdlingResource {
            val apiService = serviceGenerator.createService(ApiService::class.java)
            return when (val response = processCall { apiService.getSourcesByCategory(category) }) {
                is NewsResponse -> {
                    Resource.Success(data = response)
                }
                else -> {
                    Resource.DataError(errorCode = response as Int)
                }
            }
        }
    }

    override suspend fun getNewsBySources(
        sources: String,
        pageSize: Int,
        page: Int,
        q: String
    ): Resource<NewsResponse> {
        wrapEspressoIdlingResource {
            val apiService = serviceGenerator.createService(ApiService::class.java)
            return when (val response = processCall {
                apiService.getNewsBySources(sources, pageSize, page, q)
            }) {
                is NewsResponse -> {
                    Resource.Success(data = response)
                }
                else -> {
                    Resource.DataError(errorCode = response as Int)
                }
            }
        }
    }

}
