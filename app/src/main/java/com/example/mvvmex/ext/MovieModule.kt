package com.example.mvvmex.ext

import android.content.Context
import androidx.room.Room
import com.example.mvvmex.data.MovieRepositoryImpl
import com.example.mvvmex.data.local.MovieLocalRepositoryImpl
import com.example.mvvmex.data.local.room.MovieDatabase
import com.example.mvvmex.data.remote.MovieApi
import com.example.mvvmex.data.remote.MovieRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {
    private const val BASE_URL = "https://openapi.naver.com"

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", "R7xr3Xke_F6QCWDR_e7Y")
                    .addHeader("X-Naver-Client-Secret", "HJ7utuRxRb")
                    .build()
            )
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "movie.db").build()

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()

    @Singleton
    @Provides
    fun provideRepository(remote: MovieRemoteRepositoryImpl, local: MovieLocalRepositoryImpl) =
        MovieRepositoryImpl(remote, local)

}
