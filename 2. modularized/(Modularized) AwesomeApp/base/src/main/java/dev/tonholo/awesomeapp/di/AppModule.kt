package dev.tonholo.awesomeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tonholo.awesomeapp.BuildConfig
import dev.tonholo.awesomeapp.data.remote.UnsplashApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val ongoing = chain.request().newBuilder()

                // You should set ext.api_credentials.UNSPLASHED_API under api_credentials.gradle on your root folder
                ongoing.addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASHED_API}")

                chain.proceed(ongoing.build())
            }
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.unsplash.com")
            .build()

    @Provides
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)
}
