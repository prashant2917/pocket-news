package com.pocket.pocketnews.utils

import com.pocket.pocketnews.model.Category
import org.json.JSONArray
import org.json.JSONObject

class JsonParserUtils {

    fun parseJsonObject(result:String):JSONObject{
        return JSONObject(result)

    }

    private fun parseJsonObjectFromArray(jsonArray: JSONArray, index:Int):JSONObject{
       return jsonArray.getJSONObject(index)
    }

    fun parseJsonArray(name:String,jsonObject: JSONObject):JSONArray{
        return jsonObject.getJSONArray(name)
    }


    fun bindJsonToCategoryModel(jsonArray: JSONArray): ArrayList<Category> {
        val categoryList = ArrayList<Category>()
        if (jsonArray!=null) {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObjectCategory = parseJsonObjectFromArray(jsonArray, i)
                val category = Category()
                category.id = jsonObjectCategory.getString(KEY_CATEGORY_ID)
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
        const val KEY_CATEGORY_ID="ID"
        const val KEY_CATEGORY_DEFAULT_URL="defaulturl"
        const val KEY_CATEGORY_NAME="name"
        const val KEY_CATEGORY_DEFAULT_NAME="defaultname"
        const val KEY_CATEGORY_SECTION_URL="sectionurl"
        const val KEY_CATEGORY_SUBSECTIONS="subsections"
        const val KEY_CATEGORY_TEMPLATE="template"
        const val KEY_CATEGORY_ICON="Icon"

        const val KEY_CATEGORY_JSON_ARRAY_ITEM="Item"


        val instance: JsonParserUtils by lazy { HOLDER.INSTANCE }
    }
}
