package com.example.githubapp.ui.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WebViewModel : ViewModel() {

    private val webViewEventChannel = Channel<WebViewEvents>()
    val webViewEvents = webViewEventChannel.receiveAsFlow()

    fun openPage(args: WebViewFragmentArgs) = viewModelScope.launch{
        if (args.pageInfo == OPEN_PROFILE) {
            val url = args.repoInfo.owner.html_url
            webViewEventChannel.send(WebViewEvents.LaunchWebView(url))
        } else {
            val url = getUrl(args)
            webViewEventChannel.send(WebViewEvents.LaunchWebView(url))
        }
    }

    private fun getUrl(args: WebViewFragmentArgs) =
        if (args.repoInfo.owner.type == PROFILE_TYPE_USER) {
            "${args.repoInfo.owner.html_url}?tab=repositories"
        } else "https://github.com/orgs/${args.repoInfo.owner.login}/repositories"


    sealed class WebViewEvents {
        data class LaunchWebView(val url: String) : WebViewEvents()
    }
}

private const val PROFILE_TYPE_USER = "User"
private const val OPEN_PROFILE = "profile"