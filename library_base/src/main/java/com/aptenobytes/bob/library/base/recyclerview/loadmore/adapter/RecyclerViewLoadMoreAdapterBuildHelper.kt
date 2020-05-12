package com.aptenobytes.bob.library.base.recyclerview.loadmore.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@FormDsl
class RecyclerViewLoadMoreAdapterBuildHelper<T, VH: RecyclerView.ViewHolder>
@JvmOverloads constructor(
    var items: ArrayList<T?>,
    var withOnCreateLoadingViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH,
    var withOnCreateViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH,
    var withOnBindViewHolder: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, holder: VH, position: Int) -> Unit,
    var withGetItemCount: (adapter: RecyclerViewLoadMoreAdapter<T, VH>) -> Int,
    var withGetItemViewType: (adapter: RecyclerViewLoadMoreAdapter<T, VH>, position: Int) -> Int
) {

    init {
        initializeFormBuildHelper()
    }

    lateinit var adapter: RecyclerViewLoadMoreAdapter<T, VH>

    private fun initializeFormBuildHelper() {
        this.adapter = RecyclerViewLoadMoreAdapter()
        this.adapter.let {
            it.items = items
            it.withOnCreateViewHolder = withOnCreateViewHolder
            it.withOnCreateLoadingViewHolder = withOnCreateLoadingViewHolder
            it.withOnBindViewHolder = withOnBindViewHolder
            it.withGetItemCount = withGetItemCount
            it.withGetItemViewType = withGetItemViewType
        }
    }

}
