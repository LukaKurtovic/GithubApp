package com.example.githubapp.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.githubapp.api.GithubApi
import com.example.githubapp.data.GithubAppDatabase
import com.example.githubapp.helpers.SharedPrefs
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        provideRetrofit()
    }

    single {
        provideGithubApi(get())
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            GithubAppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single {
        val db = get<GithubAppDatabase>()
        db.getEntryDao()
    }

    single {
        SavedStateHandle()
    }

    single {
        SharedPrefs(get())
    }

}

fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(GithubApi.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

private const val DATABASE_NAME = "database"


