package com.example.githubapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val detailsEventChannel = Channel<DetailsEvents>()
    val detailsEvents = detailsEventChannel.receiveAsFlow()

    fun onProfileClick() = viewModelScope.launch {
        detailsEventChannel.send(DetailsEvents.NavigateToProfile)
    }

    fun onRepositoriesClick() = viewModelScope.launch {
        detailsEventChannel.send(DetailsEvents.NavigateToRepositories)
    }

    sealed class DetailsEvents {
        object NavigateToProfile : DetailsEvents()
        object NavigateToRepositories : DetailsEvents()
    }
}
