package com.vikination.universitylistapp.di

import android.content.Context
import androidx.room.Room
import com.vikination.universitylistapp.data.DefaultUniversityRepository
import com.vikination.universitylistapp.data.UniversityRepository
import com.vikination.universitylistapp.data.source.local.AppDatabase
import com.vikination.universitylistapp.data.source.local.UniversityDao
import com.vikination.universitylistapp.data.source.network.ApiService
import com.vikination.universitylistapp.data.source.network.ConstNetwork
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import com.vikination.universitylistapp.data.source.network.UniversityNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "University.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUniversityDao(database: AppDatabase) :UniversityDao = database.universityDao()
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @Singleton
    @Provides
    fun provideRetrofit() :Retrofit{
        return Retrofit.Builder()
            .baseUrl(ConstNetwork.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) :ApiService{
        return retrofit.create(ApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkSourceModule{

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(universityNetworkDataSource: UniversityNetworkDataSource):NetworkDataSource

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Singleton
    @Binds
    abstract fun bindUniversityRepository(repository: DefaultUniversityRepository): UniversityRepository
}
