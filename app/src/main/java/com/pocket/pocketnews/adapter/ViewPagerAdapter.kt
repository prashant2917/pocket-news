package com.pocket.pocketnews.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pocket.pocketnews.model.Category
import com.pocket.pocketnews.ui.fragments.NewsListFragment
import com.pocket.pocketnews.utils.Constants
import java.util.*

class ViewPagerAdapter/* constructor(fm: FragmentManager, behavior: Int) : super(fm, behavior)*/(
    fm: FragmentManager,
    behavior: Int,
    private var categoryList: ArrayList<Category>
) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putParcelable(
            Constants.KEY_OBJECT_CATEGORY,
            categoryList[position]
        )
        val fragment: Fragment = NewsListFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categoryList[position].name
    }


}