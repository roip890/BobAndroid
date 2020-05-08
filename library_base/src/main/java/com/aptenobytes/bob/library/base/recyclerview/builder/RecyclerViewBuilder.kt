package com.aptenobytes.bob.library.base.recyclerview.builder

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter

@DslMarker
annotation class RecyclerViewDsl
fun <T, VH : RecyclerView.ViewHolder> recycleView(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerViewAdapter<T, VH>): RecyclerView {
    recyclerView.let {
        it.itemAnimator = DefaultItemAnimator()
        it.layoutManager = layoutManager
        it.adapter = adapter
    }
    return recyclerView
}

fun RecyclerViewBuildHelper<*, *>.withLayoutManager(layoutManager: RecyclerView.LayoutManager): RecyclerView.LayoutManager {
    return setLayoutManager(layoutManager)
}

fun <T, VH : RecyclerView.ViewHolder> RecyclerViewBuildHelper<T, VH>.withAdapter(adapter: RecyclerViewAdapter<T, VH>)
        : RecyclerViewAdapter<T, VH> {
    return setAdapter(adapter)
}
