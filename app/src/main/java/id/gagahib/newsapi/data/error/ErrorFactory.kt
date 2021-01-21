package id.gagahib.newsapi.data.error


interface ErrorFactory {
    fun getError(errorCode: Int): Error
}