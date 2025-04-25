package com.madkit.themoviedbwithhilt.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.madkit.themoviedbwithhilt.BuildConfig
import com.madkit.themoviedbwithhilt.api.ApiServices
import com.madkit.themoviedbwithhilt.utils.Constants.API_KEY
import com.madkit.themoviedbwithhilt.utils.Constants.BASE_URL
import com.madkit.themoviedbwithhilt.utils.Constants.NETWORK_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun ProvideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun ConnectionTimeOut() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun ProvideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun ProvideOKHttpClient() = if (BuildConfig.DEBUG) {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder().addQueryParameter("api_key", API_KEY).build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }
        OkHttpClient.Builder().addInterceptor(requestInterceptor).addInterceptor(logginInterceptor).build()
    } else {
        OkHttpClient.Builder().build()

    }
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder().baseUrl(baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiServices::class.java)
}