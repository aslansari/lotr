package com.aslansari.lotr.di

import android.content.Context
import com.aslansari.lotr.BuildConfig
import com.aslansari.lotr.feature.character.data.CharacterService
import com.aslansari.lotr.network.AccessToken
import com.aslansari.lotr.network.AuthorizationInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

const val DISK_CACHE_SIZE = 10 * 1024 * 1024 // 10MB
const val CACHE_DURATION_MINUTES = 10L

/**
 * This module contains singleton providers for remote service needs
 */
@Module
@InstallIn(SingletonComponent::class)
object PubgServiceModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return httpLoggingInterceptor
    }

    @Provides
    @AuthInterceptor
    fun provideAuthInterceptor(): Interceptor {
        return AuthorizationInterceptor(AccessToken("Bearer", BuildConfig.API_KEY))
    }

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector,
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .redactHeaders("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    fun provideChuckerCollector(
        @ApplicationContext context: Context,
    ): ChuckerCollector {
        return ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    }

    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @AuthInterceptor authInterceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor,
        gson: Gson,
    ): Retrofit {
        // Install an HTTP cache in the application cache directory.
        var cache: Cache? = null
        // Install an HTTP cache in the application cache directory.
        try {
            val cacheDir = File(context.cacheDir, "apiResponses")
            cache = Cache(cacheDir, DISK_CACHE_SIZE.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))

        val defaultOkHttpClient: OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()

        val okHttpClientBuilder: OkHttpClient.Builder = defaultOkHttpClient.newBuilder()


        val modifiedOkHttpClient: OkHttpClient = okHttpClientBuilder
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(authInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = retrofitBuilder.apply {
            client(modifiedOkHttpClient)
            baseUrl(BuildConfig.API_BASE_URL)
        }.build()
        return retrofit
    }

    @Provides
    fun provideCharacterService(
        retrofit: Retrofit,
    ): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor
