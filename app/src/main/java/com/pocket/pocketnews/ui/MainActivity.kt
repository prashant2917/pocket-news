package com.pocket.pocketnews.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pocket.pocketnews.R
import com.pocket.pocketnews.application.PocketNewsApplication
import com.pocket.pocketnews.viewmodel.PocketNewsViewModel

class MainActivity : AppCompatActivity() {
    companion object{
        val TAG=MainActivity::class.java.simpleName
    }
    private lateinit var pocketNewsViewModel: PocketNewsViewModel
    private lateinit var pocketNewsApplication: PocketNewsApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }
    private fun init(){
        pocketNewsApplication= application as PocketNewsApplication
      pocketNewsViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(pocketNewsApplication).create(PocketNewsViewModel::class.java)
        getNewsByCategory()
    }

    /*fun getBreakingNews(){
        GlobalScope.launch {Dispatchers.IO
          val result=  ApiCaller.instance.getBreakingNews()
            Log.d(TAG,"mainactivity result is $result")
        }

    }*/

    private fun getNewsByCategory(){

        pocketNewsViewModel.getNewsByCategory().observe(this, Observer {
            Log.d(TAG,"mainactivity result is $it")
        })

    }
}
