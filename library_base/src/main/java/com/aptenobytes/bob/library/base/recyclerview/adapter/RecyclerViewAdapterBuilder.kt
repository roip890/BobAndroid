package com.aptenobytes.bob.library.base.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@DslMarker
annotation class FormDsl
fun <T, VH : RecyclerView.ViewHolder> adapter(
    adapter: RecyclerViewAdapter<T, VH> = RecyclerViewAdapter<T, VH>(),
    items: ArrayList<T?>,
    withOnCreateViewHolder: (adapter: RecyclerViewAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH,
    withOnBindViewHolder: (adapter: RecyclerViewAdapter<T, VH>, holder: VH, position: Int) -> Unit,
    withGetItemCount: (adapter: RecyclerViewAdapter<T, VH>) -> Int,
    withGetItemViewType: (adapter: RecyclerViewAdapter<T, VH>, position: Int) -> Int): RecyclerViewAdapter<T, VH> {
    adapter.let {
        it.items = items
        it.withOnCreateViewHolder = withOnCreateViewHolder
        it.withOnBindViewHolder = withOnBindViewHolder
        it.withGetItemCount = withGetItemCount
        it.withGetItemViewType = withGetItemViewType
    }
    return adapter
}
