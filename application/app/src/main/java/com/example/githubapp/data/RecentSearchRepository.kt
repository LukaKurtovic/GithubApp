package com.example.githubapp.data

import com.example.githubapp.data.model.RecentSearch

class RecentSearchRepository(private val recentSearchDao: RecentSearchDao) {

    fun getSearchSuggestions(query: String) = recentSearchDao.getSuggestions(query)
    suspend fun cacheQuery(recentSearch: RecentSearch) = recentSearchDao.insert(recentSearch)
    suspend fun deleteHistory() = recentSearchDao.deleteSearchHistory()

}