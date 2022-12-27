package com.example.githubapp.data

import android.content.Context
import com.example.githubapp.R
import com.example.githubapp.api.GithubApi
import com.example.githubapp.api.GithubResponse
import com.example.githubapp.helpers.Resource
import retrofit2.HttpException
import java.io.IOException

class GithubApiRepository(private val githubApi: GithubApi, private val context: Context) {
    suspend fun getRepos(query: String): Resource<GithubResponse> {
        return try {
            val response = githubApi.searchRepositories(query)
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(context.getString(R.string.http_exception_error_message))
        } catch (e: IOException) {
            Resource.Error(context.getString(R.string.IO_exception_error_message))
        } catch (e: Exception) {
            Resource.Error(context.getString(R.string.default_error_message))
        }
    }
}