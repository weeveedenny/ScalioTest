package com.example.scaliotest

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.scaliotest.api.ApiService
import com.example.scaliotest.data.SearchRepository
import com.example.scaliotest.ui.ViewModelFactory

 // Class that handles object creation.
 // Like this, objects can be passed as parameters in the constructors
object Injection {
    private fun provideGithubRepository(): SearchRepository {
        return SearchRepository(ApiService.create())
    }

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository())
    }
}
