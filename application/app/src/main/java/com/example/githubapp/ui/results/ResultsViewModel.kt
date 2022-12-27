package com.example.githubapp.ui.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.api.GithubResponse
import com.example.githubapp.data.GithubApiRepository
import com.example.githubapp.data.model.GithubResponseState
import com.example.githubapp.helpers.Resource
import com.example.githubapp.helpers.SharedPrefs
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ResultsViewModel(
    private val githubApiRepository: GithubApiRepository,
    sharedPrefs: SharedPrefs
) : ViewModel() {

    private var _state = MutableLiveData<GithubResponseState>()
    val state = _state
    private val lastSearchQuery = sharedPrefs.getLastSearchQuery()

    private val resultEventChannel = Channel<ResultEvents>()
    val resultEvents = resultEventChannel.receiveAsFlow()

    init {
        getRepos(lastSearchQuery)
    }

    private fun getRepos(query: String) = viewModelScope.launch {
        when (val response = githubApiRepository.getRepos(query)) {
            is Resource.Success -> {
                _state.value = GithubResponseState(response = response.data, isLoading = false)
            }
            is Resource.Error -> {
                _state.value = GithubResponseState(
                    error = response.message!!,
                    isLoading = false
                )
            }
        }
    }

    fun showResult(state: GithubResponseState?) = viewModelScope.launch {
        if (state?.error == "") {
            resultEventChannel.send(ResultEvents.ShowRepositories(state.response!!))
        } else {
            resultEventChannel.send(ResultEvents.ShowErrorMessage(state!!.error))
        }
    }

    sealed class ResultEvents {
        data class ShowErrorMessage(val message: String) : ResultEvents()
        data class ShowRepositories(val response: GithubResponse) : ResultEvents()
    }
}

