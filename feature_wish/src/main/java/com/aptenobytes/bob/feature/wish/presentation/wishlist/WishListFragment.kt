package com.aptenobytes.bob.feature.wish.presentation.wishlist

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.SetWishStatusFragment
import com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.WishViewHolder
import com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.WishViewModel
import com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.toViewModel
import com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.wishesAdapter
import com.aptenobytes.bob.feature.wish.presentation.wishsettings.WishSettingsFragment
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.recyclerview.builder.recycleView
import com.aptenobytes.bob.library.base.recyclerview.loadmore.adapter.RecyclerViewLoadMoreAdapter
import com.aptenobytes.bob.library.base.recyclerview.loadmore.listener.RecyclerViewLoadMoreScrollListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_wish_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber


class WishListFragment : BaseContainerFragment(), WishListView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: WishListViewModel by instance<WishListViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_list

    private lateinit var adapter: RecyclerViewLoadMoreAdapter<WishViewModel, WishViewHolder>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var loadMoreListener: RecyclerViewLoadMoreScrollListener
    private var loadAmount = 20

    private val stateObserver = Observer<WishListViewState> { render(it) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        setupSwipeRefreshLayout()
        setupWishesList()
        setupFloatingActionButton()
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
                    flowOf(WishListIntent.GetWishListIntent(index = 0, limit = loadAmount, refresh = true))
                        .onEach { viewModel.processIntent(it) }
                        .launchIn(lifecycleScope)
                }
            }
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupWishesList() {
        val context = requireContext()
        adapter = wishesAdapter(
            lifecycleOwner = viewLifecycleOwner,
            onDebouncedClickListener = {wish ->
                wish?.let {
                    val setWishStatusFragment = SetWishStatusFragment.newInstance(
                        wish = wish,
                        onChangeStatusListener = {wishAfterChange ->
                            this.adapter.items.forEachIndexed {index, wishFromList ->
                                if (wishFromList?.id?.value == wish.id) {
                                    wishFromList.status.value = wishAfterChange?.status
                                }
                                this.adapter.notifyItemChanged(index)
                            }
                        }
                    )
                    setWishStatusFragment.show(parentFragmentManager, SetWishStatusFragment.TAG)
                }
            }
        )
        layoutManager = LinearLayoutManager(context)
        loadMoreListener = RecyclerViewLoadMoreScrollListener(layoutManager as LinearLayoutManager).apply {
            this.onLoadMore = {
                loadMoreListener.keepLoad = true
                adapter.addLoadingView()
                flowOf(WishListIntent.GetWishListIntent(index = adapter.itemCount, limit = loadAmount, refresh = false))
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
        }
    }

    private fun setupFloatingActionButton() {
        this.floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_filter).apply {
                sizeDp = 28
                paddingDp = 4
            }
        )
        this.floatingActionButton.setOnClickListener {
            val wishSettingsFragment = WishSettingsFragment.newInstance()
            wishSettingsFragment.show(parentFragmentManager, WishSettingsFragment.TAG)
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
        flowOf(WishListIntent.InitialIntent(index = 0, limit = loadAmount))
    )

    private fun render(viewState: WishListViewState) {
        if (viewState.error == null && !viewState.isLoading) {
            if (viewState.wishes.isEmpty()) {
                this.loadMoreListener.keepLoad = false
                this.adapter.removeLoadingView()
            } else {
                val previousItemCount = adapter.items.size
                if (viewState.refresh) {
                    adapter.items = viewState.wishes.map { it.toViewModel() }.toArrayList()
                } else {
                    adapter.removeLoadingView()
                    adapter.items.addAll(viewState.wishes
                        .filter { newWish -> !adapter.items.map { it?.id?.value }.contains(newWish.id) }.map {
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
        errorAnimation.visible = viewState.error != null
        if (!viewState.isLoading) {
            swipeRefreshLayout.isRefreshing = false
        }
        recyclerView.visible = viewState.error == null && !viewState.isLoading
    }

}
