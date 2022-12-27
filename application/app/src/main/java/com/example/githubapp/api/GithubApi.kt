package com.example.githubapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val BASE_URL = " https://api.github.com/"
        const val SORT_BY_STARS = "stars"
    }

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = SORT_BY_STARS
    ): GithubResponse
}