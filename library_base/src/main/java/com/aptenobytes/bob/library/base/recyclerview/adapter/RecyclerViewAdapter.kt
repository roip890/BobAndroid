package com.aptenobytes.bob.library.base.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewAdapter<T, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var items: List<T> = listOf()
        set(value) { field = value; notifyDataSetChanged() }

    lateinit var withOnCreateViewHolder: (adapter: RecyclerViewAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH
    lateinit var withOnBindViewHolder: (adapter: RecyclerViewAdapter<T, VH>, holder: VH, position: Int) -> Unit
    lateinit var withGetItemCount: (adapter: RecyclerViewAdapter<T, VH>) -> Int
    lateinit var withGetItemViewType: (adapter: RecyclerViewAdapter<T, VH>, position: Int) -> Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return withOnCreateViewHolder(this, parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        return withOnBindViewHolder(this, holder, position)
    }

    override fun getItemCount(): Int = withGetItemCount(this)

    override fun getItemViewType(position: Int): Int {
        return withGetItemViewType(this, position)
    }

}
