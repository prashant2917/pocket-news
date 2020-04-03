package com.pocket.pocketnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pocket.pocketnews.R
import com.pocket.pocketnews.network.ApiCaller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
   val TAG=MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNewsByCategory()
    }

    fun getBreakingNews(){
        GlobalScope.launch {Dispatchers.IO
          val result=  ApiCaller.instance.getBreakingNews()
            Log.d(TAG,"mainactivity result is $result")
        }

    }

    fun getNewsByCategory(){
        GlobalScope.launch {Dispatchers.IO
            val result=  ApiCaller.instance.getNewsByCategory()
            Log.d(TAG,"category result is $result")
        }
    }
}
