package com.pocket.pocketnews.repository

interface Repository {
suspend fun getNewsByCategory():String
suspend fun getBreakingNews():String
suspend fun getNewsItem(url:String):String
suspend fun getSubSections(url:String):String
}