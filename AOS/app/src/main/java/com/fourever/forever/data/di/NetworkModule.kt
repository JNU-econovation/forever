package com.fourever.forever.data.di

import com.fourever.forever.data.AiApiRetrofit
import com.fourever.forever.data.AiApiService
import com.fourever.forever.data.FileApiRetrofit
import com.fourever.forever.data.FileApiService
import com.fourever.forever.data.datasource.FileDataSource
import com.fourever.forever.data.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://43.202.203.133:8080/"
    private const val AI_URL = "http://127.0.0.1:8000/"


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100,TimeUnit.SECONDS)
        .writeTimeout(100,TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    @FileApiRetrofit
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()

    @Singleton
    @Provides
    @AiApiRetrofit
    fun providesAiRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()


    @Singleton
    @Provides
    fun provideFileApiService(@FileApiRetrofit retrofit: Retrofit): FileApiService =
        retrofit.create(FileApiService::class.java)

    @Singleton
    @Provides
    fun provideAiFileApiService(@AiApiRetrofit retrofit: Retrofit): AiApiService =
        retrofit.create(AiApiService::class.java)

    @Singleton
    @Provides
    fun provideFileDataSource(fileApiService: FileApiService, aiApiService: AiApiService): FileDataSource =
        FileDataSource(fileApiService, aiApiService)
}