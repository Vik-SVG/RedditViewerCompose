package com.priesniakov.redditviewercompose.core.data

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response

abstract class BasePagingDataSource<RESPONSE : Any, ITEM : Any>(
    private val call: suspend (key: String, limit: String) -> Response<RESPONSE>,
    private val onLoadSuccess: ((response: Response<RESPONSE>) -> LoadResult.Page<String, ITEM>)
) : PagingSource<String, ITEM>() {

    override fun getRefreshKey(state: PagingState<String, ITEM>): String?  //null
    {
        val anchor = state.anchorPosition
        return if (anchor != null) state.closestPageToPosition(anchor)?.prevKey
            ?: state.closestPageToPosition(anchor)?.nextKey
        else null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ITEM> {
        val refreshKey = params.key.toString()
        val responseLimit = params.loadSize.toString()

        return try {
            val response = call(refreshKey, responseLimit)
            if (response.isSuccessful) {
                val body = response.body()
                Log.i("BasePagingDataSource", body.toString())
                if (body != null) {
                    onLoadSuccess(response)
                } else {
                    networkError(response)
                }
            } else {
                networkError(response)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun networkError(response: Response<RESPONSE>): LoadResult.Error<String, ITEM> =
        LoadResult.Error(NetworkErrorException("${response.code()} ${response.message()}"))
}