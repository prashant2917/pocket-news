package com.pocket.pocketnews.utils

import com.pocket.pocketnews.model.Category
import org.json.JSONArray
import org.json.JSONObject

class JsonParserUtils {

    fun parseJsonObject(result:String):JSONObject{
        return JSONObject(result)

    }

    fun parseJsonObjectFromArray(jsonArray: JSONArray,index:Int):JSONObject{
       return jsonArray.getJSONObject(index)
    }

    fun parseJsonArray(name:String,jsonObject: JSONObject):JSONArray{
        return jsonObject.getJSONArray(name)
    }


    fun bindJsonToCategoryModel(jsonArray: JSONArray): ArrayList<Category> {
        var categoryList = ArrayList<Category>()
        if (jsonArray!=null) {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObjectCategory = parseJsonObjectFromArray(jsonArray, i)
                var category = Category()
                category.ID = jsonObjectCategory.getString(KEY_CATEGORY_ID)
                category.defaultName = jsonObjectCategory.getString(KEY_CATEGORY_DEFAULT_NAME)
                category.name = jsonObjectCategory.getString(KEY_CATEGORY_NAME)
                category.defaultUrl = jsonObjectCategory.getString(KEY_CATEGORY_DEFAULT_URL)
                category.sectionUrl = jsonObjectCategory.getString(KEY_CATEGORY_SECTION_URL)
                category.subsections = jsonObjectCategory.getString(KEY_CATEGORY_SUBSECTIONS)
                category.template = jsonObjectCategory.getString(KEY_CATEGORY_TEMPLATE)
                category.icon = jsonObjectCategory.getString(KEY_CATEGORY_ICON)
                categoryList.add(category)
            }
        }
        return categoryList

    }


    private object HOLDER {
        val INSTANCE = JsonParserUtils()
    }

    companion object {
        val KEY_CATEGORY_ID="ID"
        val KEY_CATEGORY_DEFAULT_URL="defaulturl"
        val KEY_CATEGORY_NAME="name"
        val KEY_CATEGORY_DEFAULT_NAME="defaultname"
        val KEY_CATEGORY_SECTION_URL="sectionurl"
        val KEY_CATEGORY_SUBSECTIONS="subsections"
        val KEY_CATEGORY_TEMPLATE="template"
        val KEY_CATEGORY_ICON="Icon"

        val KEY_CATEGORY_JSON_ARRAY_ITEM="Item"


        val TAG= JsonParserUtils.javaClass.simpleName
        val instance: JsonParserUtils by lazy { HOLDER.INSTANCE }
    }
}
