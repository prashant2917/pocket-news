package com.pocket.pocketnews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pocket.pocketnews.R
import com.pocket.pocketnews.model.NewsItem
import kotlinx.android.synthetic.main.row_recyler_news.view.*

class NewsItemAdapter(_context: Context, _newsItemList: ArrayList<NewsItem>) :
    RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder>() {
    private var newsItemList = _newsItemList
    private var context: Context = _context

    class NewsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_recyler_news, parent, false)
        return NewsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsItemList.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.itemView.tv_headline.text = newsItemList[position].headLine
        val photo = newsItemList[position].photo


        Glide.with(context)
            .load(photo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.no_image_available)
            .into(holder.itemView.iv_headline)
    }
}