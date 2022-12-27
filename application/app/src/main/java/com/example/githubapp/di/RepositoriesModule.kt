package com.example.githubapp.di

import com.example.githubapp.data.GithubApiRepository
import com.example.githubapp.data.RecentSearchRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single {
        RecentSearchRepository(get())
    }

    single {
        GithubApiRepository(get(), get())
    }
}