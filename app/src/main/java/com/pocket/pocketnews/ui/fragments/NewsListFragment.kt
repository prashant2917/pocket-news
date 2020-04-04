package com.pocket.pocketnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pocket.pocketnews.R
import com.pocket.pocketnews.adapter.NewsItemAdapter
import com.pocket.pocketnews.application.PocketNewsApplication
import com.pocket.pocketnews.model.Category
import com.pocket.pocketnews.model.NewsItem
import com.pocket.pocketnews.ui.activities.MainActivity
import com.pocket.pocketnews.utils.Constants
import com.pocket.pocketnews.utils.JsonParserUtils
import com.pocket.pocketnews.viewmodel.PocketNewsViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*

/**
 * A simple [Fragment] subclass.
 */
class NewsListFragment : Fragment() {
    private var category: Category? = null
    private lateinit var pocketNewsViewModel: PocketNewsViewModel
    private lateinit var pocketNewsApplication: PocketNewsApplication
    private lateinit var _activity: FragmentActivity
    private var newsItemList=ArrayList<NewsItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        category = bundle?.getParcelable(Constants.KEY_OBJECT_CATEGORY)
        init()



    }

    /**
     * init views and variables
     */
    private fun init() {
        _activity = this!!.activity!!
        pocketNewsApplication = _activity.application as PocketNewsApplication
        pocketNewsViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(pocketNewsApplication)
                .create(PocketNewsViewModel::class.java)
         if(category!!.subsections.equals(Constants.SUBSECTION_NO)){
             getNewsItem()
         }
    }

    /**
     * get news item
     */
    private fun getNewsItem(){
          if(!category!!.defaultUrl.isNullOrEmpty()) {
              pocketNewsViewModel.getNewsItem(category!!.defaultUrl).observe(this, Observer {
                  Log.d(MainActivity.TAG, "${category!!.name}NewsListFragment result is $it")
                  val jsonObject = JsonParserUtils.instance.parseJsonObject(it)
                  if (jsonObject != null) {
                      val jsonArray =
                          JsonParserUtils.instance.parseJsonArray(
                              JsonParserUtils.KEY_CATEGORY_JSON_ARRAY_NEWS_ITEM,
                              jsonObject
                          )
                      if (jsonArray != null) {
                          newsItemList = JsonParserUtils.instance.bindJsonToNewsItemModel(jsonArray)
                          setUpRecyclerView()
                      }
                      else{

                      }
                  }

              })
          }

    }

    /**
     * init and setup recyclerview
     */
    private fun setUpRecyclerView(){
         lateinit var linearLayoutManager: LinearLayoutManager
        linearLayoutManager = LinearLayoutManager(_activity)
        recyler_news.layoutManager = linearLayoutManager
        val newsItemAdapter=NewsItemAdapter(_activity,newsItemList)
        recyler_news.adapter=newsItemAdapter

    }

}
