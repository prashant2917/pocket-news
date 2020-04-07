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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pocket.pocketnews.R
import com.pocket.pocketnews.adapter.NewsItemAdapter
import com.pocket.pocketnews.application.PocketNewsApplication
import com.pocket.pocketnews.model.Category
import com.pocket.pocketnews.model.NewsItem
import com.pocket.pocketnews.model.Subsection
import com.pocket.pocketnews.utils.Constants
import com.pocket.pocketnews.utils.JsonParserUtils
import com.pocket.pocketnews.utils.Utils
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
    private var subsectionList=ArrayList<Subsection>()
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
        _activity = this.activity!!
        pocketNewsApplication = _activity.application as PocketNewsApplication
        pocketNewsViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(pocketNewsApplication)
                .create(PocketNewsViewModel::class.java)
        swipe_layout.isRefreshing=true
        swipe_layout.setOnRefreshListener(refreshListener)
        Log.d("###"," category subsection ${category!!.name} ${category!!.subsections}")
        if(!hasSubSection()) {
            getNewsItem(category!!.defaultUrl)
        }

    }

    /**
     * get news item
     */
    private fun getNewsItem(url:String){
        if(url.isNotEmpty()) {
              pocketNewsViewModel.getNewsItem(url).observe(this, Observer {
                  Log.d(TAG, "${category!!.name} NewsListFragment news result is $it")
                 // progress_bar.visibility=View.GONE
                  swipe_layout.isRefreshing=false
                  val jsonObject = JsonParserUtils.instance.parseJsonObject(it)
                  if (jsonObject != null) {
                      val jsonArray =
                          JsonParserUtils.instance.parseJsonArray(
                              JsonParserUtils.KEY_CATEGORY_JSON_ARRAY_NEWS_ITEM,
                              jsonObject
                          )
                      if (jsonArray != null) {
                          Log.d("###","newslist json array not null")
                          newsItemList = JsonParserUtils.instance.bindJsonToNewsItemModel(jsonArray)
                          setUpRecyclerView()
                      }
                      else{
                         //todo load subsection news
                          Log.d("###","newslist json array  null")
                           if(hasSubSection()){
                               Log.d("###","has subsection")
                               if(subsectionList.isEmpty()) {
                                   Log.d("###"," subsection list empty")
                                   loadSubSection()
                               }
                               else{
                                   Log.d("###"," subsection list not empty")
                                   val length=subsectionList.size
                                   val randomNumber=Utils.generateRandomNumber(length)
                                   Log.d("###"," random number is $randomNumber")
                                   val url=subsectionList[randomNumber].url
                                   Log.d("###","sub section url $url")
                                   getNewsItem(subsectionList[Utils.generateRandomNumber(length)].url)
                               }
                           }

                      }

                  }

              })
          }

    }

    /**
     * init and setup recyclerview
     */
    private fun setUpRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(_activity)
        recyler_news.layoutManager = linearLayoutManager
        val newsItemAdapter=NewsItemAdapter(_activity,newsItemList)
        recyler_news.adapter=newsItemAdapter

    }

    private fun loadSubSection(){
        pocketNewsViewModel.getSubSections(category!!.sectionUrl).observe(this,
            Observer {
                Log.d(TAG, "${category!!.name} NewsListFragment subsection result is $it")

                val jsonObject = JsonParserUtils.instance.parseJsonObject(it)
                if (jsonObject != null) {
                    Log.d("###","subsection json object not null")
                    val jsonArray =
                        JsonParserUtils.instance.parseJsonArray(
                            JsonParserUtils.KEY_JSON_ARRAY_SUBSECTION_ITEM,
                            jsonObject
                        )
                    if (jsonArray != null) {
                        Log.d("###","subsection json array not null")
                        subsectionList = JsonParserUtils.instance.bindJsonToSubSection(jsonArray)
                        if(subsectionList.isNotEmpty()) {
                            getNewsItem(subsectionList[0].url)
                        }

                    }
                    else{
                        Log.d("###","subsection json array  null")

                    }

                }
                else{
                    Log.d("###","subsection json object  null")
                }

            })

    }

    companion object{
        val TAG= NewsListFragment::class.java.simpleName
    }

    private val refreshListener= SwipeRefreshLayout.OnRefreshListener {
        if(!hasSubSection()) {
            getNewsItem(category!!.defaultUrl)
        }
    }

    private fun hasSubSection():Boolean{
      return category!!.subsections==Constants.SUBSECTION_YES
    }
}
