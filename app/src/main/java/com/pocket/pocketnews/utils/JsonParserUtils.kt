package com.pocket.pocketnews.utils

import com.pocket.pocketnews.model.Category
import com.pocket.pocketnews.model.NewsItem
import org.json.JSONArray
import org.json.JSONObject

class JsonParserUtils {

      fun parseJsonObject(result:String): JSONObject? {
          return try {
              if(result.isEmpty()){
                  null
              } else {
                  JSONObject(result)
              }
          } catch (e: Exception) {
              null
          }

      }

    private fun  parseJsonObject(jsonObject: JSONObject,name:String): String {

        return try {
            if(!jsonObject.has(name) || name.isEmpty()){
                  ""
             }
             else{
                 jsonObject.getString(name)
             }
        } catch (e: Exception) {
          ""
        }
    }

    private fun  parseJsonObjectFromObject(jsonObject: JSONObject,name:String): JSONObject? {

        return try {
            if(!jsonObject.has(name) || name.isEmpty()){
                return null
            }
            else{
                jsonObject.getJSONObject(name)
            }
        } catch (e: Exception) {
          return null
        }
    }

    private fun parseJsonObjectFromArray(jsonArray: JSONArray, index:Int): JSONObject? {
        return try {
            jsonArray.getJSONObject(index)
        } catch (e: Exception) {
            null
        }
    }

    fun parseJsonArray(name:String,jsonObject: JSONObject): JSONArray? {
        return try {
            val objects=jsonObject.get(name)
            if(objects is JSONArray){
                jsonObject.getJSONArray(name)
            } else{
                null
            }
        } catch (e: Exception) {
            null
        }
    }


    fun bindJsonToCategoryModel(jsonArray: JSONArray): ArrayList<Category> {
        val categoryList = ArrayList<Category>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObjectCategory = parseJsonObjectFromArray(jsonArray, i)
                val category = Category()
                category.id = parseJsonObject(jsonObjectCategory!!,KEY_CATEGORY_ID)
                category.defaultName = parseJsonObject(jsonObjectCategory,KEY_CATEGORY_DEFAULT_NAME)
                category.name = parseJsonObject(jsonObjectCategory, KEY_CATEGORY_NAME)
                category.defaultUrl = parseJsonObject(jsonObjectCategory,KEY_CATEGORY_DEFAULT_URL)
                category.sectionUrl = parseJsonObject(jsonObjectCategory,KEY_CATEGORY_SECTION_URL)
                category.subsections = parseJsonObject(jsonObjectCategory,KEY_CATEGORY_SUBSECTIONS)
                category.template = parseJsonObject(jsonObjectCategory,KEY_CATEGORY_TEMPLATE)
                category.icon = parseJsonObject(jsonObjectCategory,KEY_CATEGORY_ICON)
                if(category.name!=Constants.CATEGORY_VIDEOS) {
                    categoryList.add(category)
                }
            }
        } catch (e: Exception) {
        }
        return categoryList

    }


    fun bindJsonToNewsItemModel(jsonArray: JSONArray): ArrayList<NewsItem> {
        val newsItemList = ArrayList<NewsItem>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObjectNewsItem = parseJsonObjectFromArray(jsonArray, i)
                val newsItem = NewsItem()
                newsItem.agency = parseJsonObject(jsonObjectNewsItem!!,KEY_NEWS_ITEM_AGENCY)
                newsItem.caption = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_CAPTION)
                newsItem.dateLine = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_DATE_LINE)
                newsItem.headLine = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_HEADLINE)
                newsItem.keyword = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_KEYWORD)
                newsItem.newsItemId = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_ID)
                newsItem.story = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_STORY)
                newsItem.webURL = parseJsonObject(jsonObjectNewsItem,KEY_NEWS_ITEM_WEB_URL)
                val jsonObjectImage=parseJsonObjectFromObject(jsonObjectNewsItem,KEY_NEWS_ITEM_IMAGE)
                if (jsonObjectImage != null) {
                    newsItem.photo=parseJsonObject(jsonObjectImage,KEY_NEWS_ITEM_PHOTO)
                    newsItem.photoCaption=parseJsonObject(jsonObjectImage,KEY_NEWS_ITEM_PHOTO_CAPTION)
                    newsItem.thumb=parseJsonObject(jsonObjectImage,KEY_NEWS_ITEM_THUMB)
                }
                else {

                    newsItem.photo = parseJsonObject(jsonObjectNewsItem, KEY_NEWS_ITEM_PHOTO)
                    newsItem.photoCaption =
                        parseJsonObject(jsonObjectNewsItem, KEY_NEWS_ITEM_PHOTO_CAPTION)
                    newsItem.thumb = parseJsonObject(jsonObjectNewsItem, KEY_NEWS_ITEM_THUMB)
                }
                newsItemList.add(newsItem)
            }
        } catch (e: Exception) {
        }
        return newsItemList

    }



    private object HOLDER {
        val INSTANCE = JsonParserUtils()
    }

    companion object {
        const val KEY_CATEGORY_ID="ID"
        const val KEY_CATEGORY_DEFAULT_URL="defaulturl"
        const val KEY_CATEGORY_NAME="name"
        const val KEY_CATEGORY_DEFAULT_NAME="defaultname"
        const val KEY_CATEGORY_SECTION_URL="sectionurl"
        const val KEY_CATEGORY_SUBSECTIONS="subsections"
        const val KEY_CATEGORY_TEMPLATE="template"
        const val KEY_CATEGORY_ICON="Icon"
        const val KEY_CATEGORY_JSON_ARRAY_ITEM="Item"

        //news item
        const val  KEY_NEWS_ITEM_ID:String="NewsItemId"
        const val KEY_NEWS_ITEM_HEADLINE: String="HeadLine"
        const val KEY_NEWS_ITEM_AGENCY: String="Agency"
        const val KEY_NEWS_ITEM_DATE_LINE:String="DateLine"
        const val KEY_NEWS_ITEM_WEB_URL:String="WebURL"
        const val KEY_NEWS_ITEM_CAPTION:String="Caption"
        const val KEY_NEWS_ITEM_IMAGE= "Image"
        const val KEY_NEWS_ITEM_PHOTO= "Photo"
        const val KEY_NEWS_ITEM_THUMB= "Thumb"
        const val KEY_NEWS_ITEM_PHOTO_CAPTION="PhotoCaption"
        const val KEY_NEWS_ITEM_KEYWORD:String="Keyword"
        const val KEY_NEWS_ITEM_STORY:String="Story"
        const val KEY_CATEGORY_JSON_ARRAY_NEWS_ITEM="NewsItem"


        val instance: JsonParserUtils by lazy { HOLDER.INSTANCE }
    }
}
