package com.aptenobytes.bob.feature.wish.presentation.wishlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.presentation.wishlist.recyclerview.adapter.*
import com.aptenobytes.bob.feature.wish.presentation.wishsettings.WishSettingsFragment
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.builder.recycleView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_wish_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance

class WishListFragment : BaseContainerFragment(), WishListView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: WishListViewModel by instance<WishListViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_list

    private lateinit var adapter: RecyclerViewAdapter<WishViewModel, WishViewHolder>

    private val stateObserver = Observer<WishListViewState> { render(it) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        setupSwipeRefreshLayout()
        setupWishesList()
        setupFloatingActionButton()
        bind()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            flowOf(WishListIntent.GetWishListIntent)
                .onEach { viewModel.processIntent(it) }
                .launchIn(lifecycleScope)
        }
    }

    private fun setupWishesList() {
        val context = requireContext()
        adapter = wishesAdapter(lifecycleOwner = viewLifecycleOwner)
        recycleView(
            recyclerView,
            LinearLayoutManager(context),
            adapter
        ).apply {
            setHasFixedSize(true)
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
        flowOf(WishListIntent.InitialIntent)
    )

    private fun render(viewState: WishListViewState) {
        adapter.items = viewState.wishes.map { it.toViewModel() }
        progressBar.visible = viewState.isLoading
        errorAnimation.visible = viewState.error != null
        if (!viewState.isLoading) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

}
