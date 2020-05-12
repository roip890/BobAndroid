package com.aptenobytes.bob.library.base.recyclerview.loadmore.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@DslMarker
annotation class FormDsl
fun <T, VH : RecyclerView.ViewHolder> loadMoreAdapter(
    adapter: RecyclerViewLoadMoreAdapter<T, VH> = RecyclerViewLoadMoreAdapter<T, VH>(),
    items: ArrayList<T?>,
    withOnCreateViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH,
    withOnCreateLoadingViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder,
    withOnBindViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, holder: VH, position: Int) -> Unit,
    withGetItemCount: (adapter: RecyclerViewLoadMoreAdapter<T, VH>) -> Int,
    withGetItemViewType: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, position: Int) -> Int): RecyclerViewLoadMoreAdapter<T, VH> {
    adapter.let {
        it.items = items
        it.withOnCreateLoadingViewHolder = withOnCreateLoadingViewHolder
        it.withOnCreateViewHolder = withOnCreateViewHolder
        it.withOnBindViewHolder = withOnBindViewHolder
        it.withGetItemCount = withGetItemCount
        it.withGetItemViewType = withGetItemViewType
    }
    return adapter
}
