package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.WishStatusViewHolder
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.WishStatusViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.toViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.wishStatusAdapter
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

class SetWishStatusFragment : BaseContainerFragment(), SetWishStatusView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: SetWishStatusViewModel by instance<SetWishStatusViewModel>()

    override val layoutResourceId = R.layout.fragment_set_wish_status

    private lateinit var adapter: RecyclerViewAdapter<WishStatusViewModel, WishStatusViewHolder>

    private val stateObserver = Observer<SetWishStatusViewState> { render(it) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        setupWishesList()
        bind()
    }

    private fun setupWishesList() {
        val context = requireContext()
        adapter = wishStatusAdapter(lifecycleOwner = viewLifecycleOwner)
        recycleView(
            recyclerView,
            LinearLayoutManager(context),
            adapter
        ).apply {
            setHasFixedSize(true)
        }
        adapter.items = listOf(
            WishStatusType.WAITING.toViewModel(),
            WishStatusType.PENDING.toViewModel(),
            WishStatusType.IN_PROGRESS.toViewModel(),
            WishStatusType.DONE.toViewModel()
        )
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
        flowOf<SetWishStatusIntent>()
    )

    private fun render(viewState: SetWishStatusViewState) {
        progressBar.visible = viewState.isLoading
        errorAnimation.visible = viewState.error != null
        if (!viewState.isLoading) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

}
