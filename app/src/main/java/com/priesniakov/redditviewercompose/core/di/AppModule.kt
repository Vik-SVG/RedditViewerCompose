package com.priesniakov.redditviewercompose.core.di

import com.priesniakov.redditviewercompose.BuildConfig
import com.priesniakov.redditviewercompose.core.data.DefaultInterceptor
import com.priesniakov.redditviewercompose.data.remote.RedditApi
import com.priesniakov.redditviewercompose.data.remote.RedditRemoteDataSource
import com.priesniakov.redditviewercompose.data.remote.RedditRemoteDataSourceImpl
import com.priesniakov.redditviewercompose.data.repository.RedditRepository
import com.priesniakov.redditviewercompose.data.repository.RedditRepositoryImp
import com.priesniakov.redditviewercompose.domain.RedditPostsUseCase
import com.priesniakov.redditviewercompose.domain.RedditPostsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.addInterceptor(DefaultInterceptor())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRedditApi(retrofit: Retrofit) =
        retrofit.create(RedditApi::class.java)

    private const val TIMEOUT_SEC: Long = 10
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Binds
    abstract fun provideRedditRepository(repo: RedditRepositoryImp): RedditRepository

    @Binds
    abstract fun provideRedditRemoteDataSource(dataSource: RedditRemoteDataSourceImpl): RedditRemoteDataSource

    @Binds
    abstract fun provideRedditPostsUseCase(useCase: RedditPostsUseCaseImpl): RedditPostsUseCase
}
