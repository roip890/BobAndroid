package com.aptenobytes.bob.library.base.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@FormDsl
class RecyclerViewAdapterBuildHelper<T, VH: RecyclerView.ViewHolder>
@JvmOverloads constructor(
    var items: ArrayList<T>,
    var withOnCreateViewHolder: (adapter: RecyclerViewAdapter<T, VH>, parent: ViewGroup, viewType: Int) -> VH,
    var withOnBindViewHolder: (adapter: RecyclerViewAdapter<T, VH>, holder: VH, position: Int) -> Unit,
    var withGetItemCount: (adapter: RecyclerViewAdapter<T, VH>) -> Int,
    var withGetItemViewType: (adapter: RecyclerViewAdapter<T, VH>, position: Int) -> Int
) {

    init {
        initializeFormBuildHelper()
    }


    lateinit var adapter: RecyclerViewAdapter<T, VH>


    private fun initializeFormBuildHelper() {
        this.adapter = RecyclerViewAdapter()
        this.adapter.let {
            it.items = items
            it.withOnCreateViewHolder = withOnCreateViewHolder
            it.withOnBindViewHolder = withOnBindViewHolder
            it.withGetItemCount = withGetItemCount
            it.withGetItemViewType = withGetItemViewType
        }
    }

}
