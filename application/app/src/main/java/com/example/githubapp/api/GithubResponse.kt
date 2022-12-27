package com.example.githubapp.api

import com.example.githubapp.data.model.GithubRepository
import com.example.githubapp.helpers.NumbersFormatterHelper

data class GithubResponse(
    val total_count: Int,
    val items: List<GithubRepository>
) {

    val totalCountFormatted: String
        get() = when (total_count) {
            in 0..999 -> total_count.toString()
            in 1000..9999 -> NumbersFormatterHelper.thousandFormat.format(total_count)
            in 10000..99999 -> NumbersFormatterHelper.tenThousandFormat.format(total_count)
            in 100000..999999 -> NumbersFormatterHelper.hundredThousandFormat.format(total_count)
            else -> NumbersFormatterHelper.millionFormat.format(total_count)
        }
}
