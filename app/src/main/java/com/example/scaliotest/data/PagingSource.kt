package com.example.scaliotest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.scaliotest.api.ApiService
import com.example.scaliotest.model.User
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val IN_QUALIFIER = "in:name,description"
private const val NETWORK_PAGE_SIZE = 9

class PagingSource(
    private val service: ApiService,
    private val query: String
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition((anchorPosition))?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER

        return try {
            val response = service.searchUsers(apiQuery, position, params.loadSize)
            val users = response.items
            val nextKey = if (users.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = users.sortedWith(compareBy{ it.login }),
                nextKey = nextKey,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}


