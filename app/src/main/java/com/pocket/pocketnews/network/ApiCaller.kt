package com.pocket.pocketnews.network

import android.util.Log
import com.pocket.pocketnews.utils.Utils
import java.net.HttpURLConnection
import java.net.URL

class ApiCaller {
 val TAG=ApiCaller.javaClass.simpleName
  lateinit var httpURLConnection: HttpURLConnection
    //Breaking news
    val BREAKING_NEWS_URL= "https://devru-times-of-india.p.rapidapi.com/feeds/breakingnewsfeed.cms?feedtype=sjson"
    val CATEGORY_NEWS_URL="https://devru-times-of-india.p.rapidapi.com/feeds/feedurllist.cms?catagory="
    suspend fun getBreakingNews():String{

      httpURLConnection=getHttpUrlConnection(BREAKING_NEWS_URL)
        val responseCode=httpURLConnection.responseCode
        Log.d(TAG,"response code is $responseCode")
       return (if(responseCode==HttpURLConnection.HTTP_OK) {
         Utils.convertInputStreamToString(httpURLConnection.inputStream)

       }
             else{
            httpURLConnection.errorStream
       }).toString()


    }

    suspend fun getNewsByCategory():String{

        httpURLConnection=getHttpUrlConnection(CATEGORY_NEWS_URL)
        val responseCode=httpURLConnection.responseCode
        Log.d(TAG,"response code is $responseCode")
        return (if(responseCode==HttpURLConnection.HTTP_OK) {
            Utils.convertInputStreamToString(httpURLConnection.inputStream)

        }
        else{
            httpURLConnection.errorStream
        }).toString()
    }

    fun getHttpUrlConnection(serviceURL: String):HttpURLConnection{
        val myURL = URL(serviceURL)
        val myURLConnection: HttpURLConnection = myURL.openConnection() as HttpURLConnection

        //  httpURLConnection.connectTimeout=CONNECTION_TIME_OUT
        myURLConnection.setRequestMethod("GET")
        myURLConnection.setRequestProperty ("x-rapidapi-host", RAPID_API_HOST);
        myURLConnection.setRequestProperty("x-rapidapi-key", RAPID_API_KEY);


        myURLConnection.setDoInput(true);


        return  myURLConnection
    }



    private object HOLDER {
        val INSTANCE = ApiCaller()
    }

    companion object {
        val RAPID_API_KEY="6e381e8272msh39f3910db4c60d1p138f94jsn2a973e6f590d"
        val RAPID_API_HOST="devru-times-of-india.p.rapidapi.com"
        val CONNECTION_TIME_OUT=5*1000

        val instance: ApiCaller by lazy { HOLDER.INSTANCE }
    }
}
