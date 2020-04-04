package com.pocket.pocketnews.model

import android.os.Parcel
import android.os.Parcelable

class Category() :Parcelable {
var name:String=""
var defaultName:String=""
var defaultUrl:String=""
var sectionUrl:String=""
var subsections:String=""
var template :String=""
var icon:String=""
var id:String=""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        defaultName = parcel.readString().toString()
        defaultUrl = parcel.readString().toString()
        sectionUrl = parcel.readString().toString()
        subsections = parcel.readString().toString()
        template = parcel.readString().toString()
        icon = parcel.readString().toString()
        id = parcel.readString().toString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(defaultName)
        dest?.writeString(defaultUrl)
        dest?.writeString(sectionUrl)
        dest?.writeString(subsections)
        dest?.writeString(sectionUrl)
        dest?.writeString(template)
        dest?.writeString(icon)
        dest?.writeString(id)
    }

    override fun describeContents(): Int {
       return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }

}