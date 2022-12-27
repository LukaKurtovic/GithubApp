package com.example.githubapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepository(
    val id: Int,
    val full_name: String,
    val name: String,
    val description: String,
    val stargazers_count: Int,
    val watchers_count: Int,
    val open_issues_count: Int,
    val private: Boolean,
    val owner: OwnerInfo
) : Parcelable {
    @IgnoredOnParcel
    val starsFormatted: String
        get() = when (stargazers_count) {
            in 0..999 -> stargazers_count.toString()
            else -> format(stargazers_count)
        }

    @Parcelize
    data class OwnerInfo(
        val login: String,
        val avatar_url: String,
        val html_url: String,
        val type: String
    ) : Parcelable

    private fun format(number: Int): String {
        val whole: Int = number / 1000
        val decimal: Int = (number % 1000) / 100

        return "${whole},${decimal}K"
    }
}