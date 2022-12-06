package com.hassan.elsayed.ammer.asteroidradar.network

import com.hassan.elsayed.ammer.asteroidradar.utilities.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private fun apiKeyInterceptor(it: Interceptor.Chain): Response {
    val originalRequest = it.request()
    val originalHttpUrl = originalRequest.url()

    val newHttpUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", Constants.API_KEY)
        .build()

    val newRequest = originalRequest.newBuilder()
        .url(newHttpUrl)
        .build()

    return it.proceed(newRequest)
}

val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { apiKeyInterceptor(it) }
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()

interface ApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAllAsteroids(): String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): String
}


object RetrofitInstance {
    val createApiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
