package com.example.simplemoviecatalog.core

import com.example.simplemoviecatalog.data.network.APIKeyInterceptor
import com.example.simplemoviecatalog.data.network.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//alcance a nivel de toda la aplicacion
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        //esta variable es opcional.
        //registra las solicitudes y respuestas HTTP realizadas por la aplicación.
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // Crea el interceptor de clave de API
        val apiKeyInterceptor = APIKeyInterceptor()

        // Crea la instancia de OkHttpClient y agrega los interceptores
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor) // Agrega el interceptor de clave de API
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}