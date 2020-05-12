package com.aptenobytes.bob.library.base.recyclerview.loadmore.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class RecyclerViewLoadMoreAdapter<T, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<T?> = arrayListOf()
        set(value) {
            field = value; notifyDataSetChanged()
        }

    lateinit var withOnCreateLoadingViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder
    lateinit var withOnCreateViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH
    lateinit var withOnBindViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, holder: VH, position: Int) -> Unit
    lateinit var withGetItemCount: (adapter: RecyclerViewLoadMoreAdapter<T, VH>) -> Int
    lateinit var withGetItemViewType: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, position: Int) -> Int

    companion object {
        const val VIEW_TYPE_LOADING = -1
    }

    fun addLoadingView() {
        removeLoadingView()
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoadingView() {
        //Remove loading item
        if (items.size != 0 && items[items.size - 1] == null) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType != VIEW_TYPE_LOADING) {
            withOnCreateViewHolder(this, parent, viewType)
        } else {
            withOnCreateLoadingViewHolder(this, parent, viewType)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType != VIEW_TYPE_LOADING) {
            return withOnBindViewHolder(this, holder as VH, position)
        }
    }

    override fun getItemCount(): Int = withGetItemCount(this)

    override fun getItemViewType(position: Int): Int {
        return items[position]?.let {
            withGetItemViewType(this, position)
        } ?: run {
            VIEW_TYPE_LOADING
        }
    }

}

class BasicLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
