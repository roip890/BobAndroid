package com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.listener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewLoadMoreScrollListener : RecyclerView.OnScrollListener {

    private var visibleThreshold = 5
    var onLoadMore: (() -> Unit)? = null
    var keepLoad: Boolean = true
    var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var layoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        totalItemCount = layoutManager.itemCount

        if (layoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions =
                (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions)
        } else if (layoutManager is GridLayoutManager) {
            lastVisibleItem = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if (!isLoading && keepLoad && totalItemCount <= lastVisibleItem + visibleThreshold) {
            onLoadMore?.invoke()
        }

    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}
