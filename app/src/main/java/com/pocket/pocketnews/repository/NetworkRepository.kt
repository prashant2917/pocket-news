package com.pocket.pocketnews.repository

import com.pocket.pocketnews.network.ApiCaller

class NetworkRepository :Repository {
    override suspend fun getNewsByCategory(): String {
        return ApiCaller.instance.getNewsByCategory()
    }

    override suspend fun getBreakingNews(): String {
        //testing purpose
      return ApiCaller.instance.getBreakingNews()
    }

    private object HOLDER {
        val INSTANCE = NetworkRepository()
    }

    companion object {


        val instance: NetworkRepository by lazy { HOLDER.INSTANCE }
    }
}