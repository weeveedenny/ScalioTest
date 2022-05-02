package com.example.scaliotest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.scaliotest.api.ApiService
import com.example.scaliotest.model.User
import kotlinx.coroutines.flow.Flow

 //SearchRepository class that works with local and remote data sources.
class SearchRepository(private val service: ApiService) {

     //Search repositories whose names match the query, exposed as a stream of data that will emit
     // every time we get more data from the network.
    fun getSearchResultStream(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = INITIAL_LOAD_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingSource(service,query)
            }
        ).flow
    }


    companion object {
        const val INITIAL_LOAD_PAGE_SIZE = 9
    }
}
