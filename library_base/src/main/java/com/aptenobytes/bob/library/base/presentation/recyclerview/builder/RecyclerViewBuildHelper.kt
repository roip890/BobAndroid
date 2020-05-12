package com.aptenobytes.bob.library.base.presentation.recyclerview.builder

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.RecyclerViewAdapter

@RecyclerViewDsl
class RecyclerViewBuildHelper<T, VH: RecyclerView.ViewHolder>
@JvmOverloads constructor(
    var recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerViewAdapter<T, VH>
) {

    init {
        initializeRecyclerViewBuildHelper(layoutManager, adapter)
    }

    private fun initializeRecyclerViewBuildHelper(
        layoutManager: RecyclerView.LayoutManager,
        adapter: RecyclerViewAdapter<T, VH>
    ) {
        recyclerView.let {
            it.itemAnimator = DefaultItemAnimator()
            it.layoutManager = layoutManager
            it.adapter = adapter
        }
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): RecyclerView.LayoutManager {
        this.recyclerView.layoutManager = layoutManager
        return layoutManager
    }

    fun setAdapter(adapter: RecyclerViewAdapter<T, VH>): RecyclerViewAdapter<T, VH> {
        this.recyclerView.adapter = adapter
        return adapter
    }
}
