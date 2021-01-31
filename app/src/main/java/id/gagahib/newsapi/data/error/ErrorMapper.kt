package id.gagahib.newsapi.data.error

import id.gagahib.newsapi.NewsAPIApplication
import id.gagahib.newsapi.R
import javax.inject.Inject
import id.gagahib.core.data.error.ErrorMapperInterface
import id.gagahib.core.data.error.Error

class ErrorMapper @Inject constructor() : ErrorMapperInterface {

    override fun getErrorString(errorId: Int): String {
        return NewsAPIApplication.context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
                Pair(Error.NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
                Pair(Error.NETWORK_ERROR, getErrorString(R.string.network_error)),
                Pair(Error.SERVER_LIMIT, getErrorString(R.string.too_many_request)),
                Pair(Error.SERVER_LIMIT_100, getErrorString(R.string.too_many_request_100))
        ).withDefault { getErrorString(R.string.something_error) }
}