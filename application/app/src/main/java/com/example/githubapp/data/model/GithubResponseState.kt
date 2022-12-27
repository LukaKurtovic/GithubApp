package com.example.githubapp.data.model

import com.example.githubapp.api.GithubResponse

data class GithubResponseState(
    val isLoading: Boolean = true,
    val response: GithubResponse? = null,
    val error: String = ""
)
