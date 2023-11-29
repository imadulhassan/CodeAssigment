package com.codeassesment.di

import android.content.Context
import android.content.SharedPreferences
import com.codeassesment.data.local.LocalDataSource
import com.codeassesment.data.local.PreferencesManager
import com.codeassesment.data.remote.RemoteDataSource
import com.codeassesment.data.remote.UserApis
import com.codeassesment.repo.UserRepository
import com.codeassesment.repo.UserRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    val BASE_URL = "https://randomuser.me/"

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun providePreferencesManager(): PreferencesManager = PreferencesManager()

    @Provides
    fun provideLocalDataSource(
        preferencesManager: PreferencesManager,
        gson: Gson
    ): LocalDataSource {
        return LocalDataSource(preferencesManager, gson)
    }


    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApis = retrofit.create(UserApis::class.java)

    @Provides
    @Singleton
    fun providesRemoteDataSource(apiService: UserApis): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesMainRepository(
        remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource
    ): UserRepository = UserRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideGsonBuild(): Gson = Gson()


}