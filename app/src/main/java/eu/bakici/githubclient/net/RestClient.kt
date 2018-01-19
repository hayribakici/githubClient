package eu.bakici.githubclient.net

import eu.bakici.githubclient.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created on 13.01.18.
 */
object RestClient {

    private val BASE_URL = "https://api.github.com"

    private var builder: Retrofit.Builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

    fun <T> createService(serviceClass: Class<T>): T {
        val client: OkHttpClient
        val clientBuilder = OkHttpClient.Builder()


        clientBuilder.addInterceptor(createLoggingInterceptor())

        clientBuilder.connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES)
        client = clientBuilder.build()
        builder.baseUrl(BASE_URL)

        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = when {
            (BuildConfig.DEBUG) -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

}