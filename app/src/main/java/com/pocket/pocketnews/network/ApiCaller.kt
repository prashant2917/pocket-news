package com.pocket.pocketnews.network

import android.util.Log
import com.pocket.pocketnews.utils.Utils
import java.net.HttpURLConnection
import java.net.URL

class ApiCaller {

    private lateinit var httpURLConnection: HttpURLConnection
    //Breaking news

    fun getBreakingNews(): String {

        httpURLConnection = getHttpUrlConnection(BREAKING_NEWS_URL, true)
        val responseCode = httpURLConnection.responseCode
        Log.d(TAG, "response code is $responseCode")
        return (if (responseCode == HttpURLConnection.HTTP_OK) {
            Utils.convertInputStreamToString(httpURLConnection.inputStream)

        } else {
            httpURLConnection.errorStream
        }).toString()


    }

    fun getNewsByCategory(): String {

        httpURLConnection = getHttpUrlConnection(CATEGORY_NEWS_URL, true)
        val responseCode = httpURLConnection.responseCode
        Log.d(TAG, "response code is $responseCode")
        return (if (responseCode == HttpURLConnection.HTTP_OK) {
            Utils.convertInputStreamToString(httpURLConnection.inputStream)

        } else {
            httpURLConnection.errorStream
        }).toString()
    }

    fun getNewsItem(url: String): String {
        httpURLConnection = getHttpUrlConnection(url, false)
        if(httpURLConnection!=null) {
            val responseCode = httpURLConnection.responseCode
            Log.d(TAG, "response code is $responseCode")
            return (if (responseCode == HttpURLConnection.HTTP_OK && httpURLConnection.inputStream != null) {
                Utils.convertInputStreamToString(httpURLConnection.inputStream)

            } else {
                httpURLConnection.errorStream
            }).toString()
        }
        return ""
    }

    private fun getHttpUrlConnection(serviceURL: String, isHeader: Boolean): HttpURLConnection {
        val myURL = URL(serviceURL)
        val myURLConnection: HttpURLConnection = myURL.openConnection() as HttpURLConnection
        myURLConnection.connectTimeout=CONNECTION_TIME_OUT
        myURLConnection.requestMethod = "GET"
        if (isHeader) {
            myURLConnection.setRequestProperty("x-rapidapi-host", RAPID_API_HOST)
            myURLConnection.setRequestProperty("x-rapidapi-key", RAPID_API_KEY)
        }
        myURLConnection.doInput = true


        return myURLConnection
    }


    private object HOLDER {
        val INSTANCE = ApiCaller()
    }

    companion object {
        val TAG = ApiCaller.javaClass.simpleName
        const val BREAKING_NEWS_URL =
            "https://devru-times-of-india.p.rapidapi.com/feeds/breakingnewsfeed.cms?feedtype=sjson"
        const val CATEGORY_NEWS_URL =
            "https://devru-times-of-india.p.rapidapi.com/feeds/feedurllist.cms?catagory="
        const val RAPID_API_KEY = "6e381e8272msh39f3910db4c60d1p138f94jsn2a973e6f590d"
        const val RAPID_API_HOST = "devru-times-of-india.p.rapidapi.com"
        const val CONNECTION_TIME_OUT=5*1000

        val instance: ApiCaller by lazy { HOLDER.INSTANCE }
    }
}
