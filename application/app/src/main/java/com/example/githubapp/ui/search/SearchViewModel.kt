package com.example.githubapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.RecentSearchRepository
import com.example.githubapp.data.model.RecentSearch
import com.example.githubapp.helpers.SharedPrefs
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val recentSearchRepository: RecentSearchRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    val query = MutableStateFlow("")

    private val searchEventChannel = Channel<SearchEvents>()
    val searchEvents = searchEventChannel.receiveAsFlow()

    private val entries = query.flatMapLatest {
        recentSearchRepository.getSearchSuggestions(it)
    }

    val suggestions = entries.asLiveData()

    private suspend fun deleteHistory() = recentSearchRepository.deleteHistory()

    private suspend fun cacheQuery(recentSearch: RecentSearch) = recentSearchRepository.cacheQuery(recentSearch)

    fun onConfirmClick() = viewModelScope.launch { deleteHistory() }

    fun onDeleteHistoryClick() = viewModelScope.launch {
        searchEventChannel.send(SearchEvents.ShowDeleteHistoryDialog)
    }

    fun onSearchClicked() = viewModelScope.launch {
        cacheQuery(RecentSearch(query.value))
        sharedPrefs.addLastSearchQuery(query.value)
        searchEventChannel.send(SearchEvents.NavigateToDetailsScreen(query.value))
    }

    fun isQueryValid(): Boolean {
        return query.value.length > 1 && query.value.isNotBlank()
    }

    sealed class SearchEvents {
        data class NavigateToDetailsScreen(val query: String) : SearchEvents()
        object ShowDeleteHistoryDialog : SearchEvents()
    }
}