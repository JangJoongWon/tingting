package com.alsif.tingting.di


import com.alsif.tingting.BuildConfig.BASE_URL
import com.alsif.tingting.data.interceptor.AuthInterceptor
import com.alsif.tingting.data.service.HomeService
import com.alsif.tingting.data.service.LikeService
import com.alsif.tingting.data.service.SearchService
import com.alsif.tingtinqg.data.service.ReserveService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    private const val TIME: Long = 30000

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME, TimeUnit.MILLISECONDS)
        .connectTimeout(TIME, TimeUnit.MILLISECONDS)
        .addInterceptor(AuthInterceptor()) // TODO interceptor 추가 필요 (주석 해제)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson : Gson = GsonBuilder()
        .setLenient()
        .create()


        return Retrofit.Builder()
            .baseUrl(BASE_URL) // TODO BASE_URL 추가 필요
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideHomeService(
        retrofit: Retrofit
    ) : HomeService = retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideLikeService(
        retrofit: Retrofit
    ) : LikeService = retrofit.create(LikeService::class.java)

    @Singleton
    @Provides
    fun provideSearchService(
        retrofit: Retrofit
    ) : SearchService = retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun provideReserveService(
        retrofit: Retrofit
    ) : ReserveService = retrofit.create(ReserveService::class.java)
}