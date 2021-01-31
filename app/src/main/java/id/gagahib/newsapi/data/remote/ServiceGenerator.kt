package id.gagahib.newsapi.data.remote

import id.gagahib.newsapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceGenerator @Inject
constructor() {

    //Network constants
    private val timeoutConnect = 30   //In seconds
    private val timeoutRead = 30   //In seconds
    private val contentType = "Content-Type"
    private val contentTypeValue = "application/json"
    private val xAPIKey = "x-api-key"

    private val xAPIKeyValue = "a48860710e004ec385502bf5e4535338"
    private val xAPIKeyValueX = "224ff1bee7dd4bd0af9fa1559e462fb4"

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
                .header(contentType, contentTypeValue)
                .header(xAPIKey, xAPIKeyValue)
                .method(original.method, original.body)
                .build()

        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS }.level = HttpLoggingInterceptor.Level.BODY
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()

        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}