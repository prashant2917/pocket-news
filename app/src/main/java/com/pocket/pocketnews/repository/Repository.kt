package com.pocket.pocketnews.repository

interface Repository {
suspend fun getNewsByCategory():String
suspend fun getBreakingNews():String
}