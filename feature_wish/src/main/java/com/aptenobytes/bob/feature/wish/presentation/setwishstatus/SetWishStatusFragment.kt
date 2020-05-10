package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.WishStatusViewHolder
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.WishStatusViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.toViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.wishStatusAdapter
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.builder.recycleView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_wish_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance

class SetWishStatusFragment(
    val wish: WishDomainModel
) : BaseContainerFragment(), SetWishStatusView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: SetWishStatusViewModel by instance<SetWishStatusViewModel>()

    override val layoutResourceId = R.layout.fragment_set_wish_status

    private lateinit var adapter: RecyclerViewAdapter<WishStatusViewModel, WishStatusViewHolder>

    private val stateObserver = Observer<SetWishStatusViewState> { render(it) }

    companion object{
        val TAG = "SET_WISHES"
        fun newInstance(wish: WishDomainModel): SetWishStatusFragment {
            return SetWishStatusFragment(wish = wish)
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        setupWishesList()
        progressBar.visible = false
        errorAnimation.visible = false
        bind()
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // setup full height
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
//            bottomSheet?.let {
//                val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
//                bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED)
//            }
            bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
        }
        return bottomSheetDialog

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupWishesList() {
        val context = requireContext()
        adapter = wishStatusAdapter(
            lifecycleOwner = viewLifecycleOwner,
            onDebouncedClickListener = {status ->
                status?.let {
                    flowOf(SetWishStatusIntent.SetStatusIntent(wish = wish, status = status))
                        .onEach { intent -> viewModel.processIntent(intent) }
                        .launchIn(lifecycleScope)
                }
            }
        )
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
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf<SetWishStatusIntent>()
    )

    private fun render(viewState: SetWishStatusViewState) {
        progressBar.visible = viewState.isLoading
        errorAnimation.visible = viewState.error != null
        recyclerView.visible = viewState.error == null && !viewState.isLoading
    }

}
