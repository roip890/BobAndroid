package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.notification.R
import com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview.NotificationViewHolder
import com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview.NotificationViewModel
import com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview.notificationsAdapter
import com.aptenobytes.bob.feature.notification.presentation.notificationlist.recyclerview.toViewModel
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.presentation.recyclerview.builder.recycleView
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.adapter.RecyclerViewLoadMoreAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.loadmore.listener.RecyclerViewLoadMoreScrollListener
import kotlinx.android.synthetic.main.fragment_notification_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance


class NotificationListFragment : BaseContainerFragment(), NotificationListView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: NotificationListViewModel by instance<NotificationListViewModel>()

    override val layoutResourceId = R.layout.fragment_notification_list

    private lateinit var adapter: RecyclerViewLoadMoreAdapter<NotificationViewModel, NotificationViewHolder>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var loadMoreListener: RecyclerViewLoadMoreScrollListener
    private var loadAmount = 20

    private val stateObserver = Observer<NotificationListViewState> { render(it) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        setupSwipeRefreshLayout()
        setupNotificationsList()
        bind()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.colorAccent))
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.backgroundInverseColor))
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.stateLiveData.value?.isLoading?.let { isLoading ->
                if (!isLoading) {
                    this.loadMoreListener.keepLoad = true
                    flowOf(NotificationListIntent.GetNotificationListIntent(index = 0, limit = loadAmount, refresh = true))
                        .onEach { viewModel.processIntent(it) }
                        .launchIn(lifecycleScope)
                }
            }
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupNotificationsList() {
        val context = requireContext()
        adapter = notificationsAdapter(
            lifecycleOwner = viewLifecycleOwner,
            onDebouncedClickListener = {notification ->
                notification?.let {
                }
            }
        )
        layoutManager = LinearLayoutManager(context)
        loadMoreListener = RecyclerViewLoadMoreScrollListener(layoutManager as LinearLayoutManager).apply {
            this.onLoadMore = {
                loadMoreListener.keepLoad = true
                adapter.addLoadingView()
                flowOf(NotificationListIntent.GetNotificationListIntent(index = adapter.itemCount, limit = loadAmount, refresh = false))
                    .onEach { viewModel.processIntent(it) }
                    .launchIn(lifecycleScope)
            }
        }
        recycleView(
            recyclerView,
            layoutManager,
            adapter
        ).apply {
            setHasFixedSize(true)
            addOnScrollListener(loadMoreListener)
            recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL))
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf(NotificationListIntent.GetNotificationListIntent(index = 0, limit = loadAmount, refresh = true))
    )

    private fun render(viewState: NotificationListViewState) {
        if (viewState.error == null && !viewState.isLoading) {
            if (viewState.notifications.isEmpty()) {
                this.loadMoreListener.keepLoad = false
                this.adapter.removeLoadingView()
            } else {
                val previousItemCount = adapter.items.size
                if (viewState.refresh) {
                    adapter.items = viewState.notifications.map { it.toViewModel() }.toArrayList()
                } else {
                    adapter.removeLoadingView()
                    adapter.items.addAll(viewState.notifications
                        .filter { newNotification -> !adapter.items.map { it?.id?.value }.contains(newNotification.id) }.map {
                            it.toViewModel()
                        }
                    )
                    adapter.notifyItemRangeInserted(
                        previousItemCount.coerceAtLeast(0) - 1,
                        (adapter.items.size - previousItemCount).coerceAtLeast(0)
                    )
                }
                // keep fill the screen
                if (layoutManager.findLastCompletelyVisibleItemPosition() == -1
                    || (previousItemCount.coerceAtMost(layoutManager.findLastCompletelyVisibleItemPosition())
                            + (adapter.items.size - previousItemCount).coerceAtLeast(0) >= adapter.itemCount - 1)) {
                    loadMoreListener.onLoadMore?.invoke()
                }
            }
        }
        this.loadMoreListener.isLoading = viewState.isLoading
            errorAnimation.visibility = if (viewState.error != null) VISIBLE else GONE
        if (!viewState.isLoading) {
            swipeRefreshLayout.isRefreshing = false
        }
        recyclerView.visibility = if (viewState.error == null && !viewState.isLoading) VISIBLE else GONE
    }

}
