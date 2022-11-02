package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.BuildConfig
import com.example.mindicadorevaluation.api.LatestIndicatorsJsonAdapter
import com.example.mindicadorevaluation.api.LatestIndicatorsResponse
import com.example.mindicadorevaluation.api.MindicadorApi
import com.example.mindicadorevaluation.core.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @Named("api")
    fun provideRetrofit(): Retrofit {

        val moshi = Moshi.Builder()
            .add(LatestIndicatorsJsonAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    fun trackerListService(@Named("api") retrofit: Retrofit): MindicadorApi =
        retrofit.create(MindicadorApi::class.java)

}
