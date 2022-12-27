package com.example.githubapp.di

import com.example.githubapp.ui.details.DetailsViewModel
import com.example.githubapp.ui.results.ResultsViewModel
import com.example.githubapp.ui.search.SearchViewModel
import com.example.githubapp.ui.webview.WebViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        ResultsViewModel(get(), get())
    }

    viewModel {
        DetailsViewModel()
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        WebViewModel()
    }
}

