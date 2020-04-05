package com.pocket.pocketnews.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pocket.pocketnews.R
import com.pocket.pocketnews.adapter.CategoryViewPagerAdapter
import com.pocket.pocketnews.application.PocketNewsApplication
import com.pocket.pocketnews.model.Category
import com.pocket.pocketnews.utils.JsonParserUtils
import com.pocket.pocketnews.viewmodel.PocketNewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var categoryList= ArrayList<Category>()
    companion object{
        val TAG= MainActivity::class.java.simpleName
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
        setupToolbar()
        getNewsByCategory()
    }

    /*fun getBreakingNews(){
        GlobalScope.launch {Dispatchers.IO
          val result=  ApiCaller.instance.getBreakingNews()
            Log.d(TAG,"mainactivity result is $result")
        }

    }*/

    /**
     * get news data category wise
     */
    private fun getNewsByCategory(){
        progress_bar.visibility= View.VISIBLE
        pocketNewsViewModel.getNewsByCategory().observe(this, Observer {
            progress_bar.visibility= View.GONE
            Log.d(TAG,"mainactivity result is $it")
            val jsonObject=JsonParserUtils.instance.parseJsonObject(it)
            if(jsonObject!=null) {
                val jsonArray = JsonParserUtils.instance.parseJsonArray(
                    JsonParserUtils.KEY_CATEGORY_JSON_ARRAY_ITEM,
                    jsonObject
                )
                if (jsonArray != null) {
                    categoryList = JsonParserUtils.instance.bindJsonToCategoryModel(jsonArray)
                    setUpTabs()
                }
            }
        })

    }

    /**
     * setting up toolbar to activity
     */
    private fun setupToolbar(){
       setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    /**
     * setting up tabs by getting data from server
     */
    private  fun setUpTabs(){


       val viewPagerAdapter=CategoryViewPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,categoryList)
        view_pager_news.adapter=viewPagerAdapter

        tab_news_category.setupWithViewPager(view_pager_news)

    }
}
