package com.example.scaliotest.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.scaliotest.data.SearchRepository
import com.example.scaliotest.model.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchRepositoriesViewModel(
    private val repository: SearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

     //Stream of immutable states representative of the UI.
    val state: StateFlow<UiState>

     // Stream of immutable states representative of the data
    val pagingDataFlow: Flow<PagingData<User>>

     //Processor of side effects from the UI which in turn feedback into [state]
    val accept: (UiAction) -> Unit

    init {
        val initQuery = savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        val lastQueryScrolled = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart {
                emit(UiAction.Search(query = initQuery))
            }

        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            ).onStart {
                emit(UiAction.Scroll(currentQuery = lastQueryScrolled))
            }

        pagingDataFlow = searches
            .flatMapLatest {
                searchRepo(queryString = it.query)
            }.cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastScrolledQuery = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = UiState()
        )
        accept = {action ->
            viewModelScope.launch {
                actionStateFlow.emit(action )
            }
        }
    }

    private fun searchRepo(queryString: String): Flow<PagingData<User>> {
       return repository.getSearchResultStream(queryString)
    }
}


sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastScrolledQuery: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)

private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = ""
private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"