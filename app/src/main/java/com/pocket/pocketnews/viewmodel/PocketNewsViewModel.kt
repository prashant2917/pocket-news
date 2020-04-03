package com.pocket.pocketnews.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pocket.pocketnews.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PocketNewsViewModel : ViewModel() {
   private val liveDataNewsByCategory=MutableLiveData<String>()

    companion object{
        val TAG= PocketNewsViewModel::class.java.simpleName
    }
  fun getNewsByCategory():LiveData<String>{
      var result=""
      GlobalScope.launch {
          Dispatchers.Main
           result=  NetworkRepository.instance.getNewsByCategory()
          Log.d(TAG,"viewmodel result is $result")
          liveDataNewsByCategory.postValue(result)
      }

      return liveDataNewsByCategory
  }

}