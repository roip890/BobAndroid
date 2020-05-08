package com.aptenobytes.bob.feature.wish.presentation.allwishes

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.presentation.allwishes.recyclerview.adapter.*
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.builder.recycleView
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_wish_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance

class AllWishesFragment : BaseContainerFragment(), AllWishesView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: AllWishesViewModel by instance<AllWishesViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_list

    private lateinit var adapter: RecyclerViewAdapter<WishViewModel, WishViewHolder>

    private val stateObserver = Observer<AllWishesViewState> { render(it) }

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
        adapter = wishesAdapter(lifecycleOwner = viewLifecycleOwner)
        recycleView(
            recyclerView,
            LinearLayoutManager(context),
            adapter
        ).apply {
            setHasFixedSize(true)
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
        flowOf(AllWishesIntent.InitialIntent)
    )

    private fun render(viewState: AllWishesViewState) {
        adapter.items = viewState.wishes.map { it.toViewModel() }
        progressBar.visible = viewState.isLoading
        errorAnimation.visible = viewState.error != null
    }

}
